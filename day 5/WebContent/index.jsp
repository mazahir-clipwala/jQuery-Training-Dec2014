<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Index</title>
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script type="text/javascript"> 
        $(function () {

            $('#ok').on ('click', function () {

				var data = {
					name: $('input[name="name"]').val(),
					item: $('input[name="item"]').val()

						};
                
				$.post ("/OrderMgmt/orders", data, function (response, status, xHR) {
					console.log (response);
				});
			});

			$('#refresh').on ('click', function () {
				var dataType = $('input:checked').attr('id');

				if (dataType === 'xml') {
					$.get ("/OrderMgmt/orders", function (response, status, xHR) {
						var xmlObj = $.parseXML (response);
						var orders = $(xmlObj).find ("order");
						$('#orders').empty();
						$.each (orders, function (index, obj1) {
							$('#orders').append ('<span class="success">' + $(obj1).find ("name").text () + " ordered " + $(obj1).find ("item").text () + '</span>');
						});
					});
				}
				else {
					$.getJSON ('/OrderMgmt/orders/json', function (data) {
						console.log (data.orders[0]);
					});
				}
			});

			$('#orders').load ("/OrderMgmt/orders", function (response, status, xHR) {
				if (status === "error") {
					$('#orders').html ('<span class="failure">' + response + '</span>');
					return;
				}
				var xmlObj = $.parseXML (response);
				var orders = $(xmlObj).find ("order");
				$('#orders').empty();
				$.each (orders, function (index, obj1) {
					$('#orders').append ('<span class="success">' + $(obj1).find ("name").text () + " ordered " + $(obj1).find ("item").text () + '</span>');
				});
			});
               
        });
    </script>
    <style>
    	body, button, input[type="text"] {
    		font-family: Calibri;
    		font-size: 20px;
    	}
    	
    	.success {
    		font-family: inherit !important;
    		font-size: inherit !important;
    		background-color: gray;
    		color: white;
    		margin: 5px;
    		display: block;
    	}
    	
    	.failure {
    		font-family: inherit !important;
    		font-size: inherit !important;
    		background-color: red;
    		color: white;
    		margin: 5px;
    		display: block;
    	}
    </style>
	</head>
	<body>
		<div id="orders"></div>
		<table>
			<tr>
				<td>Name: </td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Item: </td>
				<td><input type="text" name="item" /></td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="radio" id="xml" name="dataType" checked="checked"/><label>Xml</label>
					<input type="radio" id="json" name="dataType" /><label>JSON</label>
				</td>
			</tr>
			
			<tr>
				<td><button id="ok">OK</button></td>
				<td><button id="refresh">Refresh</button></td>
			</tr>
		
		</table>
	
	</body>
</html>