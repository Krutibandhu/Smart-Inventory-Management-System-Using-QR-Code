import { supabase } from "./supabase-client.js";

const poTableBody = document.getElementById("poTableBody");

async function getCurrentUser() {
  const { data, error } = await supabase.auth.getUser();
  if (error || !data.user) {
    alert("⚠ Please log in as Admin first.");
    window.location.href = "/html/login.html";
    return null;
  }
  return data.user;
}

async function loadPurchaseOrders() {
  const user = await getCurrentUser();
  if (!user) return;

  const supabaseUserId = user.id;

  try {
    // ✅ Fetch items (with imports)
    const res = await fetch(`/api/items/admin/${supabaseUserId}`);
    if (!res.ok) throw new Error("Failed to fetch items");
    const items = await res.json();

    poTableBody.innerHTML = "";

    items.forEach((item, idx) => {
      if (item.quantity <= 10) {
        // ✅ Use latest import from item.imports (sorted by date if needed)
        const imports = item.imports || [];
        const latestImport = imports.length
          ? imports[imports.length - 1]
          : null;

        const vendorName = latestImport?.vendorName || "Unknown";
        const vendorId = latestImport?.vendorEntityId || "-";

        // Random reorder qty 11–30
        const reorderQty = Math.floor(Math.random() * (30 - 11 + 1)) + 11;
        const amount = reorderQty * item.price;

        const row = document.createElement("tr");
        row.innerHTML = `
          <td data-label="PO ID">#PO-${idx + 1001}</td>
          <td data-label="Item">${item.name}</td>
          <td data-label="Supplier">${vendorName} (${vendorId})</td>
          <td data-label="Items">${reorderQty}</td>
          <td data-label="Amount">₹${amount}</td>
          <td data-label="Status" class="status-pending">Pending</td>
          <td data-label="Action" class="actions">
            <button>View</button>
            <button>Approve</button>
            <button>Reject</button>
          </td>
        `;
        poTableBody.appendChild(row);
      }
    });
  } catch (err) {
    console.error("Error loading purchase orders:", err.message);
    poTableBody.innerHTML = `<tr><td colspan="7">❌ Failed to load purchase orders.</td></tr>`;
  }
}

document.addEventListener("DOMContentLoaded", loadPurchaseOrders);
