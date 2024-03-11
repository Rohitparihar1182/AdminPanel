<%-- 
    Document   : register
    Created on : 26-Feb-2024, 10:27:32 pm
    Author     : rohit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
         <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AdminPanel - Update User</title>
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
                <form method="post" action="UpdateUser">
                    <%
                        Map<String, Object> userData = (Map<String, Object>) request.getAttribute("user-data");
                    %>
                    <input type="hidden" name="uid" value="<%= userData.getOrDefault("uid", "") %>">
                    <h2 style="text-align: center; font-size: 32px;">Update User</h2>
                    <div class="input-group">

                        <label class="required-label" for="name">Name</label>
                        <input required type="text" id="name" name="name" value="<%= userData.getOrDefault("name", "")%>" placeholder="Enter your name ...">
                    </div>
                    <div class="input-group">

                        <label class="required-label" for="branch">Branch</label>
                        <input required type="text" id="branch" name="branch" value="<%= userData.getOrDefault("branch", "")%>" placeholder="Enter your branch ...">
                    </div>
                    <div class="input-group">

                        <label class="required-label" for="password">New Password</label>
                        <input required type="password" id="password" name="password" value="<%= userData.getOrDefault("password", "")%>" placeholder="Create New Password ...">
                    </div>
                    <div class="input-group">

                        <label class="required-label" for="confirm-password">Confirm New Password</label>
                        <input required type="password" id="confirm-password" name="confirm-password" value="<%= userData.getOrDefault("password", "")%>"
                            placeholder="Confirm New Password ...">
                    </div>
                    <div style="flex-direction: row;">

                        <input type="checkbox" id="hostel" name="hostel"  <% if ((boolean)userData.getOrDefault("hostel", false)) out.print("checked"); %>>
                        <label for="hostel">I am a hosteler</label>
                    </div>
                    <div style="display: block;">

                        <input type="submit" value="Submit" title="Submit">
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
