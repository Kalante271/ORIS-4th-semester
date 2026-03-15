<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Persons</title>
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
        .persons {
            width: 500px;
            text-align: center;
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .person {
            background: white;
            padding: 10px;
            border-radius: 6px;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }
        .add-button {
            display: inline-block;
            padding: 8px 15px;
            background: #2f2f2f;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 14px;
            transition: background 0.2s ease;
        }
        .add-button:hover {
            background: #3d3d3d;
        }
    </style>
</head>
<body>
<main class="content">
    <h2 class="title">Список людей</h2>
    <div class="persons">
        <#list persons as person>
            <div class="person">
                Id: ${person.id}
                Name: ${person.name}
                Phone: ${person.phone?if_exists.number!"-"}
            </div>
        </#list>
    </div>
    <a class="add-button" href="/persons/add">Добавить человека</a>
</main>
</body>
</html>