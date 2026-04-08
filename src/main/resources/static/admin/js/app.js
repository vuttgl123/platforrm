const API_BASE = "/api/v1";

const resources = [
    "platform/modules",
    "platform/entities",
    "platform/fields",
    "platform/menus",
    "platform/views",
    "runtime/records"
];

let currentResource = null;
let currentData = [];

// ===== INIT =====
window.onload = () => {
    renderSidebar();
};

// ===== SIDEBAR =====
function renderSidebar() {
    const el = document.getElementById("sidebar");

    let html = `<div class="sidebar-title">Meta Forge</div>`;

    resources.forEach(r => {
        html += `<div class="menu-item" onclick="load('${r}')">${r}</div>`;
    });

    el.innerHTML = html;
}

// ===== LOAD =====
function load(path) {
    currentResource = path;

    fetch(`${API_BASE}/${path}`)
        .then(r => r.json())
        .then(r => {
            currentData = r.data || [];
            renderTable();
        });
}

// ===== TABLE =====
function renderTable() {
    const app = document.getElementById("app");

    if (!currentData.length) {
        app.innerHTML = `<div class="card"><h4>No data</h4></div>`;
        return;
    }

    const cols = Object.keys(currentData[0]);

    let html = `
        <div class="card">

            <div class="header-bar">
                <h4>${currentResource}</h4>
                <button class="btn btn-primary" onclick="openCreate()">+ New</button>
            </div>

            <table class="table table-bordered table-hover">
                <thead>
                    <tr>
    `;

    cols.forEach(c => html += `<th>${c}</th>`);
    html += `<th style="width:140px">Action</th></tr></thead><tbody>`;

    currentData.forEach(row => {
        html += "<tr>";

        cols.forEach(c => {
            html += `<td>${format(row[c])}</td>`;
        });

        html += `
            <td>
                <button class="btn btn-sm btn-warning" onclick="openEdit(${row.id})">Edit</button>
                <button class="btn btn-sm btn-danger" onclick="remove(${row.id})">Delete</button>
            </td>
        `;

        html += "</tr>";
    });

    html += "</tbody></table></div>";

    app.innerHTML = html;
}

// ===== FORM =====
function openCreate() {
    renderForm({});
}

function openEdit(id) {
    const data = currentData.find(x => x.id === id);
    renderForm(data);
}

function renderForm(data) {
    const app = document.getElementById("app");
    const keys = Object.keys(currentData[0]);

    let html = `<div class="form-panel"><h4>Form</h4>`;

    keys.forEach(k => {
        if (k === "id") return;

        html += `
            <div class="mb-3">
                <label class="form-label">${k}</label>
                <input class="form-control"
                       id="f_${k}"
                       value="${data[k] ?? ""}">
            </div>
        `;
    });

    html += `
        <button class="btn btn-primary" onclick="submitForm(${data.id || "null"})">Save</button>
        <button class="btn btn-light" onclick="renderTable()">Cancel</button>
    </div>`;

    app.innerHTML = html;
}

// ===== SUBMIT =====
function submitForm(id) {
    const keys = Object.keys(currentData[0]);
    const body = {};

    keys.forEach(k => {
        if (k === "id") return;
        const val = document.getElementById(`f_${k}`).value;
        if (val !== "") body[k] = val;
    });

    const method = id ? "PUT" : "POST";
    const url = id
        ? `${API_BASE}/${currentResource}/${id}`
        : `${API_BASE}/${currentResource}`;

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    }).then(() => load(currentResource));
}

// ===== DELETE =====
function remove(id) {
    if (!confirm("Delete record?")) return;

    fetch(`${API_BASE}/${currentResource}/${id}`, {
        method: "DELETE"
    }).then(() => load(currentResource));
}

// ===== FORMAT =====
function format(v) {
    if (v == null) return "";
    if (typeof v === "object") return JSON.stringify(v);
    return v;
}