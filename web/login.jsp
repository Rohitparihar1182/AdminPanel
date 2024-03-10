<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AdminPanel - Login</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Mr+Dafoe&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formStyle.css">
    </head>
    <body>
        <%@ include file="./header.html" %>
        <section>
            <div class="container">
                <form method="post" action="LoginAdmin">
                    <h2 style="text-align: center; font-size: 32px;">Login</h2>

                    <div class="input-group">

                        <label class="required-label" for="id">ID</label>
                        <input required type="text" id="id" name="id" placeholder="Enter your ID ...">
                    </div>
                    <div class="input-group">

                        <label class="required-label" for="password">Password</label>
                        <input required type="password" id="password" name="password" placeholder="Enter your Password ...">
                    </div>
                    <div style="display: block;">

                        <input type="submit" value="Submit" title="Submit">
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
