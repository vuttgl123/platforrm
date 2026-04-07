<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../fragments/head.ftl">
</head>
<body>
<div class="admin-shell">
    <aside class="admin-sidebar">
        <#include "../fragments/sidebar.ftl">
    </aside>

    <div class="admin-main">
        <header class="admin-header">
            <#include "../fragments/header.ftl">
        </header>

        <main class="admin-content">
            <#include "../fragments/alert.ftl">
            <#if contentTemplate??>
                <#include contentTemplate>
            <#else>
                <div class="card">
                    <div class="card-body">
                        <p>No content template defined.</p>
                    </div>
                </div>
            </#if>
        </main>

        <footer class="admin-footer">
            <#include "../fragments/footer.ftl">
        </footer>
    </div>
</div>

<script src="/admin/js/admin.js"></script>
</body>
</html>