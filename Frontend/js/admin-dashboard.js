import { supabase } from "./supabase-client.js";

let currentAdmin;


// ✅ Ensure user stays logged in
supabase.auth.onAuthStateChange((event, session) => {
  if (!session) {
    window.location.href = "/html/login.html"; // Redirect if not logged in
  }
});

// Logout handler
document.getElementById("logoutBtn").addEventListener("click", async () => {
  const { error } = await supabase.auth.signOut();
  
  if (error) {
    console.error("Logout failed:", error.message);
    alert("Failed to logout, please try again.");
  } else {
    // Clear localStorage if you stored extra data (like admin name/photo)
    localStorage.clear();

    // Redirect to login page
    window.location.href = "/html/login.html";
  }
});

//update the dashboard
function updateDashboard(admin) {
  // Admin profile
  document.getElementById("adminDisplayName").textContent = admin.fullName;
  document.getElementById("adminImage").src = "https://i.pravatar.cc/150?u=" + admin.email;

  // Stats
  const warehouses = admin.warehouses || [];
  const activeCount = warehouses.filter(w => w.enabled).length;
  const inactiveCount = warehouses.length - activeCount;

  document.querySelector(".stat-box.wh h2").textContent = warehouses.length;
  document.querySelector(".stat-box.active h2").textContent = activeCount;
  document.querySelector(".stat-box.inactive h2").textContent = inactiveCount;

  // TODO: Alerts and PO counts (step 4)
}


// ✅ Get current logged-in Supabase user
async function getCurrentUser() {
  const { data: { user }, error } = await supabase.auth.getUser();

  if (error) {
    console.error("Error getting user:", error.message);
    return null;
  }

  if (!user) {
    window.location.href = "login.html"; // Redirect if no user
    return null;
  }

  console.log("Current logged-in user:", user);
  console.log("Supabase User ID:", user.id); // 👈 here’s your supabaseUserId
  return user;
}

// ✅ Load Admin data from backend
async function loadAdminData() {
  const user = await getCurrentUser();
  if (!user) return;

  const supabaseUserId = user.id;

  try {
    const response = await fetch(`http://localhost:8080/api/admins/${supabaseUserId}`);

    if (!response.ok) {
      throw new Error("Failed to fetch admin data");
    }

    const admin = await response.json();

    // Update dashboard UI
    // document.getElementById("adminDisplayName").textContent = admin.fullName || "Admin";
    // document.getElementById("adminImage").src =
    //   "https://i.pravatar.cc/150?u=" + supabaseUserId;

    console.log("Admin data loaded:", admin);
    currentAdmin = admin;

    updateDashboard(admin);
  } catch (err) {
    console.error(err.message);
  }
}

// Run on page load
document.addEventListener("DOMContentLoaded", loadAdminData);
