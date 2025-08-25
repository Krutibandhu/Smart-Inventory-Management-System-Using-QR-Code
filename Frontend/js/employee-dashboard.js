import { supabase } from "./supabase-client.js";

let currentEmployee;

// ✅ Ensure user stays logged in
supabase.auth.onAuthStateChange((event, session) => {
  if (!session) {
    window.location.href = "/html/employee-login.html";
  }
});

// ✅ Logout
document.getElementById("logoutBtn").addEventListener("click", async () => {
  const { error } = await supabase.auth.signOut();
  if (error) {
    console.error("Logout failed:", error.message);
    alert("Failed to logout. Try again.");
  } else {
    localStorage.clear();
    window.location.href = "/html/employee-login.html";
  }
});

// ✅ Fetch current user
async function getCurrentUser() {
  const {
    data: { user },
    error,
  } = await supabase.auth.getUser();
  if (error) {
    console.error("Error getting user:", error.message);
    return null;
  }
  return user;
}

// ✅ Load Employee data
async function loadEmployeeData() {
  const user = await getCurrentUser();
  if (!user) return;

  const supabaseUserId = user.id;
  try {
    const res = await fetch(
      `http://localhost:8080/api/employees/supabase/${supabaseUserId}`
    );
    if (!res.ok) throw new Error("Failed to fetch employee data");
    const employee = await res.json();
    console.log("Employee data:", employee);

    currentEmployee = employee;

    // Update UI
    document.getElementById("employeeDisplayName").textContent =
      employee.fullName;
    document.getElementById("employeeImage").src =
      "https://i.pravatar.cc/150?u=" + employee.email;
  } catch (err) {
    console.error("Error:", err.message);
  }
}

document.addEventListener("DOMContentLoaded", loadEmployeeData);
