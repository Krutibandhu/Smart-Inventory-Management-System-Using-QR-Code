import { supabase } from "./supabase-client";

const registrationForm = document.querySelector("#admin-registration-form");
const fullname = document.querySelector("#admin-fullname");
const email = document.querySelector("#admin-email");
const phone = document.querySelector("#admin-phone");
const password = document.querySelector("#admin-password");
const confirmpassword = document.querySelector("#admin-confirmpassword");
const distributer = document.querySelector("#admin-distributer-id");
const companyname = document.querySelector("#admin-companyname");
const whhouse = document.querySelector("#admin-wh-house");
const role = document.querySelector("#admin-role");

registrationForm.addEventListener("submit",(e)=>
{
    e.preventDefault();
    const fullnameValue = fullname.value;
    const emailValue = email.value;
    const phoneValue = phone.value;
    const passwordValue = password.value;
    const confirmpasswordValue = confirmpassword.value;
    const distributerValue = distributer.value;
    const companynameValue = companyname.value;
    const whhouseValue = whhouse.value;
    const roleValue = role.value;

      // ✅ Check values in console
    console.log("Fullname:", fullnameValue);
    console.log("Email:", emailValue);
    console.log("Phone:", phoneValue);
    console.log("Password:", passwordValue);
    console.log("Confirm Password:", confirmpasswordValue);
    console.log("Distributer ID:", distributerValue);
    console.log("Company Name:", companynameValue);
    console.log("Warehouse:", whhouseValue);
    console.log("Role:", roleValue);
})