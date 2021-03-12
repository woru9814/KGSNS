<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}


.contextmenu {
	display: none;
	position: absolute;
	width: 200px;
	margin: 0;
	padding: 0;
	background: #FFFFFF;
	border-radius: 5px;
	list-style: none;
	box-shadow: 0 15px 35px rgba(50, 50, 90, 0.1), 0 5px 15px
		rgba(0, 0, 0, 0.07);
	overflow: hidden;
	z-index: 999999;
}

.contextmenu li {
	border-left: 3px solid transparent;
	transition: ease .2s;
}

.contextmenu li a {
	display: block;
	padding: 10px;
	text-decoration: none;
	transition: ease .2s;
}

.contextmenu li:hover {
	background: #CE93D8;
	border-left: 3px solid #9C27B0;
}

.contextmenu li:hover a {
	color: #FFFFFF;
}
</style>
