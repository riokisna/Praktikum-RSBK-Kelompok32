<%-- 
    Document   : home
    Created on : Sep 22, 2019, 12:45:58 PM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Sistem Informasi Mahasiswa TEKKOM</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" >
        <style type="text/css">
            .nav-link {
                color: #fff;
            }
            li a:hover {
                color: #fff;
            }
            .navbar{    
            background-color: #4A314D;
            z-index: 100;
            }
            .container{
                margin-top: 20px;
            }
            .jumbotron {
                background-color: #6B6570;
                margin-left: 5%;
                width: 90%;
            }
            .jumbotron hr {
                color: #A8BA9A;
                border: none;
            }
            .jumbotron p {
                color: #FFF;
                padding-left: 7.5%;
                font-size: 26px;
            }
            .btn {
                float: right;
                background-color: #6fc94b;
                color: #FFF;
                width: 10%;
                height: 42px;
                margin-left: 2.5%;
            }
            .btn:hover {
                opacity: 0.8;
            }
            .table1 {
                margin-left: 5%;
                width: 90%;
            }
            .table1 td {
                width: 20%;
            }
            .table1 inputtext {
                width: 80%;
            }
        </style>


    </head>
    <body>
        <nav class="navbar navbar-expand-lg">
            <a class="navbar-brand" href="index.php">Mahasiswa TEKKOM</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">HOME <span class="sr-only">(current)</span></a>
                    </li>

                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li  class="nav-item">
                        <a href="./about.html" class="nav-link" ><%=session.getAttribute("userName")%></a>
                    </li>
                    <li class="nav-item">
                        <a href="./login.jsp" class="nav-link" >Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
                    
        <div class="container">
            <div class="jumbotron">
                <p>Data Mahasiswa</p><hr>
                <p>Modul RSBK - Kelompok32</p><hr>
                <p>Selamat Datang, <%=session.getAttribute("userName")%></p>
            </div>
            
            <div class=" daftar">
                <form action="./StudentServlet" method="POST">
                    <table class="table table1 table-bordered">
                    <tr>
                    <td>Student ID</td>
                    <td><input class="form-control inputtext" type="text" name="studentId" value="${student.studentId}" /></td>
                    </tr>
                    <tr>
                    <td>First Name</td>
                    <td><input class="form-control inputtext" type="text" name="firstname" value="${student.firstName}" /></td>
                    </tr>
                    <tr>
                    <td>Last Name</td>
                    <td><input class="form-control inputtext" type="text" name="lastname" value="${student.lastName}" /></td>
                    </tr>
                    <tr>
                    <td>Alamat</td>
                    <td><input class="form-control inputtext" type="text" name="alamat" value="${student.alamat}" /></td>
                    </tr>
                    <tr>
                    <td colspan="2">
                    <input type="submit" class="btn btn-sm" name="action" value="Search" />
                    <input type="submit" class="btn btn-sm" name="action" value="Delete" />
                    <input type="submit" class="btn btn-sm" name="action" value="Edit" />
                    <input type="submit" class="btn btn-sm" name="action" value="Add" />
                </td>
                </tr>
                </table>
            </form>
            <h3 align="center">Informasi Data</h3>
		<table class="table table1 table-bordered table-hover">
                    <thead>
                    <tr>
                    <th>No. ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Alamat</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:forEach items="${allStudents}" var="stud">
                    <tr>
                        <td>${stud.studentId}</td>
			<td>${stud.firstName}</td>
                        <td>${stud.lastName}</td>
                        <td>${stud.alamat}</td>
                    </tr>
                    </s:forEach>
                    </tbody>
                </table>
            </div>
	</div>
    </div>
</html>
