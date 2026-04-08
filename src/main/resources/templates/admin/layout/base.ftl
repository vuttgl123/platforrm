<#macro layout>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MetaForge</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

    <style>
        body {
            margin:0;
            background: radial-gradient(circle at top, #1e293b, #020617);
            color:#e2e8f0;
            font-family: Inter, sans-serif;
        }

        .sidebar {
            width:260px;
            height:100vh;
            position:fixed;
            background:#020617;
            padding:20px;
        }

        .sidebar a {
            display:block;
            padding:10px;
            border-radius:10px;
            color:#94a3b8;
            text-decoration:none;
        }

        .sidebar a:hover {
            background:#1e293b;
            color:#fff;
        }

        .main {
            margin-left:260px;
            padding:20px;
        }

        .topbar {
            margin-left:260px;
            padding:10px 20px;
            background:#020617;
            border-bottom:1px solid #1e293b;
        }

        .card {
            background: rgba(2,6,23,0.8);
            border-radius:16px;
            border:1px solid #1e293b;
        }

        .btn-primary {
            background: linear-gradient(135deg,#6366f1,#8b5cf6);
            border:none;
        }
    </style>

</head>
<body>

<#include "sidebar.ftl">
<#include "topbar.ftl">

<div class="main">
    <#nested>
</div>

</body>
</html>

</#macro>