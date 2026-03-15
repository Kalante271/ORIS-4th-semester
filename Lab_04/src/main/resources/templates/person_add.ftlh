<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить человека</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, Helvetica, sans-serif;
        }
        .content {
            background-color: #f4f4f4;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 20px;
            min-height: 100vh;
        }
        .title {
            font-size: 26px;
            color: #333;
        }
        .form {
            background: white;
            padding: 25px 30px;
            border-radius: 8px;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
            width: 350px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .form-field {
            display: flex;
            flex-direction: column;
            gap: 5px;
            font-size: 14px;
        }
        input {
            padding: 8px 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
            transition: border 0.15s ease;
        }
        input:focus {
            outline: none;
            border-color: #2f2f2f;
        }
        .submit-button {
            padding: 10px;
            background: #2f2f2f;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        .submit-button:hover {
            background: #3d3d3d;
        }
        .back-button {
            text-decoration: none;
            color: #2f2f2f;
            font-size: 14px;
            transition: color 0.2s ease;
        }
        .back-button:hover {
            color: #000;
        }
    </style>
</head>
<body>
<main class="content">
    <h2 class="title">Добавить человека</h2>
    <form class="form" method="post" action="/persons/add">
        <div class="form-field">
            <label for="name">Name:</label>
            <input id="name" name="name"/>
        </div>
        <div class="form-field">
            <label>Phone:</label>
            <input name="phone.number"/>
        </div>
        <button class="submit-button" type="submit">
            Сохранить
        </button>
    </form>
    <a class="back-button" href="/persons">Назад</a>
</main>
</body>
</html>