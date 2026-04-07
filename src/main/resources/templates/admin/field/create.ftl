<form method="post" action="/admin/fields/create">
    <#assign field = {} />
    <#include "form.ftl">

    <div class="mt-3 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="/admin/fields" class="btn btn-outline-secondary">Cancel</a>
    </div>
</form>