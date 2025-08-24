// import { supabase } from "./supabase-client";

// const registrationForm = document.querySelector("#admin-registration-form");
// const fullname = document.querySelector("#admin-fullname");
// const email = document.querySelector("#admin-email");
// const phone = document.querySelector("#admin-phone");
// const password = document.querySelector("#admin-password");
// const confirmpassword = document.querySelector("#admin-confirmpassword");
// const distributer = document.querySelector("#admin-distributer-id");
// const companyname = document.querySelector("#admin-companyname");
// const whhouse = document.querySelector("#admin-wh-house");
// const role = document.querySelector("#admin-role");

// registrationForm.addEventListener("submit",(e)=>
// {
//     e.preventDefault();
//     const fullnameValue = fullname.value;
//     const emailValue = email.value;
//     const phoneValue = phone.value;
//     const passwordValue = password.value;
//     const confirmpasswordValue = confirmpassword.value;
//     const distributerValue = distributer.value;
//     const companynameValue = companyname.value;
//     const whhouseValue = whhouse.value;
//     const roleValue = role.value;

//       // ✅ Check values in console
//     console.log("Fullname:", fullnameValue);
//     console.log("Email:", emailValue);
//     console.log("Phone:", phoneValue);
//     console.log("Password:", passwordValue);
//     console.log("Confirm Password:", confirmpasswordValue);
//     console.log("Distributer ID:", distributerValue);
//     console.log("Company Name:", companynameValue);
//     console.log("Warehouse:", whhouseValue);
//     console.log("Role:", roleValue);
// })

import { supabase } from "./supabase-client.js";

// get the form element
const registrationForm = document.querySelector("#admin-registration-form");

// when the form is submitted
registrationForm.addEventListener("submit", async (e) => {
  e.preventDefault(); // stop page reload

  // collect values from input fields
  const fullName = document.querySelector("#admin-fullname").value;
  const email = document.querySelector("#admin-email").value;
  const phoneNumber = document.querySelector("#admin-phone").value;
  const password = document.querySelector("#admin-password").value;
  const confirmPassword = document.querySelector(
    "#admin-confirmpassword"
  ).value;
  const companyName = document.querySelector("#admin-companyname").value;
  const location = document.querySelector("#admin-whadress").value;
  const warehouseName =
    document.querySelector("#admin-whname").value || "Default Warehouse";

  // check if both passwords match
  if (password !== confirmPassword) {
    alert("Passwords don’t match");
    return;
  }

  try {
    // 1. create user in Supabase Auth
    const { data, error } = await supabase.auth.signUp({
      email,
      password,
      options: { data: { full_name: fullName, role: "admin" } },
    });

    if (error) throw error;

    // Supabase gives us a unique id for this user
    const supabaseUserId = data.user.id;

    // 2. prepare admin details to send to backend
    const adminPayload = {
      fullName,
      email,
      phoneNumber,
      companyName,
      supabaseUserId, // link Supabase auth with backend data
      warehouses: [{ warehouseName,  location, enabled:true}],
    };

    // 3. call backend API to save this admin
    const res = await fetch("http://localhost:8080/api/admins", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(adminPayload),
    });

    if (!res.ok) throw new Error("Backend save failed");

    // 4. on success, good to go for login page
    window.location.href = "/html/login.html";
  } catch (err) {
    console.error(err.message);
    alert("Error: " + err.message);
  }
});
