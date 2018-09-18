<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<body>
<h1>Spring Boot - Upload Status</h1>
<#--<div th:if="${message}">-->
<#--<h2 th:text="${message}"/>-->
<div>
<#if message??>
    <h2>${message!}</h2>
</#if>
</div>
</body>
</html>