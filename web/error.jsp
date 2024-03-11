<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AdminPanel - some error occured</title>
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
                <h1 style="text-align: center;"><%= request.getAttribute("message") %></h1>
                <div class="quick-links">
                    <h3>Quick Links</h3>
                    <a href="/AdminPanel/dashboard">Dashboard</a>
                    <a href="/AdminPanel/login.jsp">Login</a>
                </div>
            </div>
        </section>
    </body>
</html>

