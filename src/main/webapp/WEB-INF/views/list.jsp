<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 <script type = "text/javascript">
 
 $(document).ready(function() {
	 function successHandler(todo) {
		 var tr = [];
		 tr.push('<tr id = \'task'+todo.id+'\'>');
		tr.push('<td>' + todo.id + '</td>');
		tr.push('<td>' + todo.task + '</td>');
		tr.push('<td>' + todo.name + '</td>');
		tr.push('<td><button class=\'edit\' id='+todo.id+'>Edit</button>&nbsp;&nbsp;<button class=\'delete\' id=' + todo.id + '>Delete</button></td>');
		tr.push('</tr><br>');
		return tr;
	 }
		$.getJSON('http://localhost:8080/list', function(todo) {
			var tr=[];
			 for (var i = 0; i < todo.length; i++) {
				 tr = successHandler(todo[i]);
				 $('#list').append($(tr.join('')));
			 }
			
		});
		$(document).delegate('#add', 'click', function(event) {
			event.preventDefault();
			var task = {id : $("#taskId").val(),task : $("#task").val(),name : $("#name").val()};
			$.ajax({
				type: "POST",
				contentType: "application/json; charset=utf-8",
				url: "http://localhost:8080/add",
				data: JSON.stringify(task),
				cache: false,
				success: function(todo) {
		          var tr = successHandler(todo);
				$('#list').append($(tr.join('')));
					$("#msg").html( "<span style='color: green'>Added task successfully</span>" );
				},
				error: function(err) {
					$("#msg").html( "<span style='color: red'>task is required</span>" );
				}
			});
		});
		$(document).delegate('.delete', 'click', function() { 
			 
			var id = $(this).attr('id');
				$.ajax({
					type: "DELETE",
					contentType: "application/text; charset=utf-8",
					url: "http://localhost:8080/delete/"+id,			
					cache: false,
					success: function(result) {
						$('#task'+id).remove();
						$("#msg").html( "<span style='color: green'>Task deleted successfully</span>" );
					},
					error: function(err) {
						$("#msg").html( "<span style='color: red'>Task is required</span>" );
					}
				});
		 });
		$(document).delegate('.edit', 'click', function() {
			
			var task = {task:$("#task").val(),id:$(this).attr('id')};
			$.ajax({
				type: "PUT",
				contentType: "application/json; charset=utf-8",
				url: "http://localhost:8080/update",
				data : JSON.stringify(task),
				cache: false,
				success: function(todo) {
					var tr = successHandler(todo);
					$('#task'+task.id).replaceWith(tr.join(''));
					$("#msg").html( "<span style='color: green'>Task updated successfully</span>" );
				},
				error: function(err) {
					$("#msg").html( "<span style='color: red'>Task not updated</span>" );
				}
			});
		});
	 });
 </script>
 <SCRIPT type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>

</head>
<body>
<BODY onload="noBack();"
    onpageshow="if (event.persisted) noBack();" onunload="">
<!-- <a href  = "list">See the list of todo's</a><br></br> -->
<div id = "list"></div>
<form action="#" method = "GET">
<span id="msg"></span>

Task <input type = "text" name = "task" id = "task" required = "required"><br>
 <input type = "hidden" name = "taskId" id = "taskId" ><br>
 <!-- Name <input type = "text" name = "name" id = "name" required = "required"><br> -->
 <input type = "button" value = "add" id = add>
</form>
<div>
<% 
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
String name = (String) session.getAttribute("name");
if(name==null) {
	response.sendRedirect("/");
}
%>
 
 <form action = "Logout" method = "GET">
 <input type = "submit" value = "logout">
 </form>

</div>

</body>
</html>