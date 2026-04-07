<form method="post" action="/admin/fields/${field.id!101}/edit">
    <#include "form.ftl">

    <div class="mt-3 d-flex gap-2">
        <button type="submit" class="btn btn-primary">Update</button>
        <a href="/admin/fields" class="btn btn-outline-secondary">Cancel</a>
    </div>
</form>