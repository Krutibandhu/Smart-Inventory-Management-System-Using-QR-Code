import { supabase } from "./supabase-client.js";

const employeeForm = document.querySelector("#employee-registration-form");

employeeForm.addEventListener("submit", async (e) => {
  e.preventDefault(); // stop page reload

  // collect employee form values
  const fullName = document.querySelector("#employee-fullname").value;
  const email = document.querySelector("#employee-email").value;
  const phoneNumber = document.querySelector("#employee-phone").value;
  const password = document.querySelector("#employee-password").value;
  const confirmPassword = document.querySelector("#employee-confirmpassword").value;
  const companyName = document.querySelector("#employee-companyname").value;

  // admin’s supabase id is needed here → pass as hidden input or from session
  const adminSupabaseUserId = document.querySelector("#admin-supabase-id").value;

  // check passwords
  if (password !== confirmPassword) {
    alert("Passwords don’t match");
    return;
  }

  try {
    // 1. create employee in Supabase Auth
    const { data, error } = await supabase.auth.signUp({
      email,
      password,
      options: { data: { full_name: fullName, role: "employee", adminId: adminSupabaseUserId } }
    });

    if (error) throw error;

    const supabaseUserId = data.user.id;

    // 2. prepare employee payload for backend
    const employeePayload = {
      fullName,
      email,
      phoneNumber,
      companyName,
      supabaseUserId
    };

    // 3. call backend API → attach employee under this admin
    const res = await fetch(`http://localhost:8080/api/employees/${adminSupabaseUserId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(employeePayload)
    });

    if (!res.ok) throw new Error("Backend save failed");

    // 4. on success, go to login page
    window.location.href = "/login.html";
  } catch (err) {
    console.error(err.message);
    alert("Error: " + err.message);
  }
});
