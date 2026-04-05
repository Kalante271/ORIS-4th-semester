<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Тест</title>
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>

<div id="login_section" style="display: block">
    <form id="form" method="post">
        <div><input name="username" type="text" placeholder="username"/></div>
        <div><input name="password" type="password" placeholder="password"/></div>
        <div><input type="button" value="OK" onclick="login()"/></div>
    </form>
</div>

<div id="content_section" style="display: none">
    <h1>Данные по бронированиям в гостинице</h1>
    <div>
        <table id="booking_table">

        </table>
    </div>
</div>

<div id="booking_data" style="display: none">

</div>

<#noparse>
<script>
    let content_section = document.getElementById("content_section");
    let login_section = document.getElementById("login_section");
    let booking_data = document.getElementById("booking_data");

    let jwt_token;

    function login() {
        const formDataToJson = (formData) => JSON.stringify(Object.fromEntries(formData));
        const formElement = document.getElementById('form');
        const jsonData = formDataToJson(new FormData(formElement));
        console.log(jsonData)

        fetch('http://localhost:8090/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: jsonData
        })
            .then(response => response.json())
            .then(json => {
                let token = json.token;
                jwt_token = token;
                // localStorage.setItem('jwt_token', token);
                // window.location = '/app';
                content_section.style.display="block";
                login_section.style.display="none";

                show_content();
            });
    }

    function show_content() {
        show_bookings()
    }

    function show_bookings() {
        fetch('http://localhost:8090/api/booking/allview', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + jwt_token
            }
        })
            .then(response => response.json())
            .then(json => {
                make_booking_table(json)
            });
    }

    function make_booking_table(data) {
        let tbl = document.getElementById("booking_table");
        tbl.innerHTML = "";

        let header = document.createElement("tr");
        header.innerHTML =
            `<th>Комната</th>
            <th>Дата заезда</th>
            <th>Дата проживания</th>`;
        tbl.append(header);

        for (let i = 0; i < data.bookings.length; i++) {
            let b = data.bookings[i];

            let tr = document.createElement('tr');
            tr.onclick = () => open_booking(b.id);

            tr.innerHTML =
                `<td>${b.room}</td>
                 <td>${b.arrivaldate}</td>
                 <td>${b.stayingdate}</td>`;

            tbl.append(tr);
        }
    }

    function open_booking(booking_id) {
        fetch('http://localhost:8090/api/booking/get/' + booking_id, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + jwt_token
            }
        })
            .then(response => response.json())
            .then(json => {
                fill_booking(json)
            });
    }

    function fill_booking(data) {
        console.log(data)
        booking_data.innerHTML =
            `<div class="form-card">
                <h3>Бронирование №${data.id}</h3>

                <input type="hidden" id="booking_id" value="${data.id}">

                <div class="form-group">
                    <label for="arrivaldate">Дата заезда:</label>
                    <input type="date" id="arrivaldate" value="${data.arrivaldate}">
                </div>

                <div class="form-group">
                    <label for="stayingdate">Дата проживания:</label>
                    <input type="date" id="stayingdate" value="${data.stayingdate}">
                </div>

                <div class="form-group">
                    <label for="departuredate">Дата выезда:</label>
                    <input type="date" id="departuredate" value="${data.departuredate ?? ''}">
                </div>

                <div class="form-group">
                    <label for="name">Имя:</label>
                    <input id="name" value="${data.name ?? ''}">
                </div>

                <div class="form-group">
                    <label>Пол:</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="gender" value="Male"
                                ${data.gender === 'Male' ? 'checked' : ''}>
                            Мужской
                        </label>
                        <label>
                            <input type="radio" name="gender" value="Female"
                                ${data.gender === 'Female' ? 'checked' : ''}>
                            Женский
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="birthDate">Дата рождения:</label>
                    <input type="date" id="birthDate" value="${data.birthDate ?? ''}">
                </div>

                <div class="button-group">
                    <button onclick="save_booking()">Сохранить</button>
                    <button onclick="goBack()">Назад</button>
                </div>
            </div>`;

        booking_data.style.display="block"
        content_section.style.display="none";
    }

    function save_booking() {
        const bookingDto = {
            id: document.getElementById("booking_id").value,
            arrivaldate: document.getElementById("arrivaldate").value,
            stayingdate: document.getElementById("stayingdate").value,
            departuredate: document.getElementById("departuredate").value,
            name: document.getElementById("name").value,
            gender: document.querySelector('input[name="gender"]:checked')?.value,
            birthDate: document.getElementById("birthDate").value
        };

        fetch('http://localhost:8090/api/booking/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': 'Bearer ' + jwt_token
            },
            body: JSON.stringify(bookingDto)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка сохранения");
            }
        })
        .then(() => {
            alert("Данные сохранены");
            show_content();
            goBack();
        })
        .catch(err => alert(err.message));
    }

    function goBack() {
        booking_data.style.display = "none";
        content_section.style.display = "block";
    }
</script>
</#noparse>


</body>
</html>