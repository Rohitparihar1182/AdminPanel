<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AdminPanel - Students List</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Mr+Dafoe&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formStyle.css">
        <style>
            #open-modal{
                width: 45px;
                outline: none;
                border: none;
                background: transparent;
                cursor: pointer;
            }
            .game {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <%@ include file="./dashboardHeader.jsp" %>
        <%@ include file="./modal.jsp" %>
        <section>
            <div class="container">
                <div style="display: flex; justify-content: space-between; align-items: center">
                    <div>
                        <h1>Welcome, <%= request.getAttribute("adminName") %></h1>
                    </div>
                    <div class="add-user">
                        <a href="/AdminPanel/add-game.jsp">Add Games</a>
                    </div>
                </div>
            </div>
        </section>
        <section>
            <div class="container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>UID</th>
                            <th>Branch</th>
                            <!--<th>Action</th>-->
                        </tr>
                    </thead>

                    <tbody>
                        <%
                        boolean status = (boolean) request.getAttribute("isSuccess");
                        if(status){
                            List<String> names = (List<String>)request.getAttribute("name");
                            List<String> branches = (List<String>) request.getAttribute("branch");
                            List<String> uids = (List<String>) request.getAttribute("uid");
                           
                            for(int i = 0; i< names.size(); i++){
                                out.println("<tr>");
                                out.println("<td>"+names.get(i)+"</td>");
                                out.println("<td>"+uids.get(i)+"</td>");
                                out.println("<td>"+branches.get(i)+"</td>");
                                
                                out.println("</tr>");
                            }
                            
                        }else{
                            out.println("<h2>Error fetching student data</h2>");
                        }
         
                        %>

                    </tbody>
                </table>
            </div>

        </section>


        <script src="./js/modal.js"></script>
    </body>
</html>

