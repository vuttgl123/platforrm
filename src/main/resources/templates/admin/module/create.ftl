<form method="post" action="/admin/modules/create">
    <#assign module = {} />
    <#include "form.ftl">

    <div class="mt-3 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="/admin/modules" class="btn btn-outline-secondary">Cancel</a>
    </div>
</form>