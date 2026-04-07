<form method="post" action="/admin/entities/create">
    <#assign entity = {} />
    <#include "form.ftl">

    <div class="mt-3 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="/admin/entities" class="btn btn-outline-secondary">Cancel</a>
    </div>
</form>