<div class="card">
    <div class="card-body">
        <div class="section-header mb-3">
            <div>
                <h5 class="mb-1">Workflow Definitions</h5>
                <p class="text-muted mb-0">Manage state machines and transitions</p>
            </div>
            <a href="/admin/workflows/create" class="btn btn-primary">Create Workflow</a>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle admin-table">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Entity</th>
                    <th>States</th>
                    <th>Transitions</th>
                    <th>Status</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>customer_onboarding</td>
                    <td>Customer Onboarding</td>
                    <td>customer</td>
                    <td>5</td>
                    <td>8</td>
                    <td><span class="badge bg-success">Published</span></td>
                    <td class="text-end">
                        <a href="#" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="#" class="btn btn-sm btn-outline-primary">Edit</a>
                    </td>
                </tr>
                <tr>
                    <td>lead_approval</td>
                    <td>Lead Approval</td>
                    <td>lead</td>
                    <td>4</td>
                    <td>6</td>
                    <td><span class="badge bg-warning text-dark">Draft</span></td>
                    <td class="text-end">
                        <a href="#" class="btn btn-sm btn-outline-secondary">View</a>
                        <a href="#" class="btn btn-sm btn-outline-primary">Edit</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>