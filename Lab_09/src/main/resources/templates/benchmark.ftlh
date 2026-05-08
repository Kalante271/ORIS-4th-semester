<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html>
    <head>
        <meta charset="UTF-8">
        <title>Бенчмарк</title>
        <style>
            .main {
                width: 800px;
                margin: 40px auto;
            }
            .controls {
                margin-bottom: 20px;
            }
            .results {
                display: flex;
                gap: 20px;
            }
            .card {
                flex: 1;
                padding: 16px;
                border: 1px solid #ccc;
                border-radius: 8px;
                display: flex;
                flex-direction: column;
                gap: 12px;
            }
            .title {
                font-weight: bold;
            }
            .value {
                font-size: 24px;
            }
            .button {
                padding: 8px 16px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <main class="main">

            <h1>Сравнение HTTP и NATS</h1>

            <div class="controls">
                <input type="file" id="image">
            </div>

            <div class="results">
                <div class="card">
                    <div class="title">HTTP</div>
                    <div id="httpResult" class="value">—</div>
                    <button id="httpBtn" class="button">Запустить</button>
                </div>

                <div class="card">
                    <div class="title">NATS</div>
                    <div id="natsResult" class="value">—</div>
                    <button id="natsBtn" class="button">Запустить</button>
                </div>
            </div>

        </main>

        <#noparse>
        <script>
            document.getElementById("httpBtn").addEventListener("click", () => runBenchmark("/api/benchmark/http", "httpResult"));

            document.getElementById("natsBtn").addEventListener("click", () => runBenchmark("/api/benchmark/nats", "natsResult"));

            async function runBenchmark(url, resultId) {
                const file = document.getElementById("image").files[0];

                if (!file) {
                    alert("Выберите изображение");
                    return;
                }

                const formData = new FormData();
                formData.append("image", file);

                const response = await fetch(url, {
                    method: "POST",
                    body: formData
                });

                const result = await response.json();

                document.getElementById(resultId).innerText = `${result.averageMs.toFixed(2)} ms`;
            }
        </script>
        </#noparse>
    </body>
</html>