<%-- 
    Document   : register
    Created on : 26-Feb-2024, 10:27:32 pm
    Author     : rohit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AdminPanel - Register</title>
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
        <%@ include file="./dashboardHeader.jsp" %>
        <section>
            <div class="container">
                <form method="post" action="CreateGame">
                    <h2 style="text-align: center; font-size: 32px;">Add Game</h2>
                    <div class="input-group">

                        <label class="required-label" for="name">Name</label>
                        <input required type="text" id="name" name="name" placeholder="Enter game name ...">
                    </div>
                    
                    <div style="display: block;">

                        <input type="submit" value="Submit" title="Submit">
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
