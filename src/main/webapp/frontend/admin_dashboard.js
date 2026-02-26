const API_BASE = "http://localhost:8080";

const logoutBtn = document.getElementById("logoutBtn");
const welcomeMsg = document.getElementById("welcomeMsg");
const userMessage = document.getElementById("userMessage");

const usersTableBody = document.querySelector("#usersTable tbody");
const addUserBtn = document.getElementById("addUserBtn");

const userModal = document.getElementById("userModal");
const modalTitle = document.getElementById("modalTitle");
const usernameInput = document.getElementById("usernameInput");
const passwordInput = document.getElementById("passwordInput");
const roleInput = document.getElementById("roleInput");
const saveUserBtn = document.getElementById("saveUserBtn");
const closeModalBtn = document.querySelector(".close");

let editUserId = null;

// Show admin name
const username = sessionStorage.getItem("username") || "Admin";
welcomeMsg.textContent = `Welcome, ${username}`;

// Logout
logoutBtn.addEventListener("click", () => {
  sessionStorage.clear();
  window.location.href = "index2.html";
});

// Open modal for adding user
addUserBtn.addEventListener("click", () => {
  editUserId = null;
  modalTitle.textContent = "Add New User";
  usernameInput.value = "";
  passwordInput.value = "";
  roleInput.value = "";
  userModal.style.display = "block";
});

// Close modal
closeModalBtn.addEventListener("click", () => {
  userModal.style.display = "none";
});

// Save user (Add or Edit)
saveUserBtn.addEventListener("click", async () => {
  const usernameVal = usernameInput.value.trim();
  const passwordVal = passwordInput.value.trim();
  const roleVal = roleInput.value;

  // Validation
  if (!usernameVal || !roleVal || (!editUserId && !passwordVal)) {
    alert("Please fill all required fields.");
    return;
  }

  try {
    let res, data;

    if (editUserId) {
      // Edit user → PUT /api/users/update
      res = await fetch(`${API_BASE}/api/users/update`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          user_id: editUserId,
          username: usernameVal,
          password: passwordVal, // Include password (backend can ignore empty)
          role: roleVal,
        }),
      });
    } else {
      // Add user → POST /api/users/add
      res = await fetch(`${API_BASE}/api/users/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: usernameVal,
          password: passwordVal,
          role: roleVal,
        }),
      });
    }

    data = await res.json();

    if (res.ok) {
      userModal.style.display = "none";
      alert(editUserId ? "User updated!" : "User added!");
      loadUsers();
    } else {
      alert(data.message || "Error saving user");
    }
  } catch (err) {
    alert("Error: " + err.message);
  }
});

// Delete user
// Delete user
async function deleteUser(userId) {
  if (!confirm("Are you sure you want to delete this user?")) return;
  try {
    // DELETE /api/users/delete?user_id=USER_ID
    const res = await fetch(`${API_BASE}/api/users/delete?user_id=${userId}`, {
      method: "DELETE",
    });
    if (res.ok) {
      alert("User deleted!");
      loadUsers();
    } else {
      const data = await res.json();
      alert(data.message || "Error deleting user");
    }
  } catch (err) {
    alert("Error: " + err.message);
  }
}

// Edit user
function editUser(user) {
  editUserId = user.user_id;
  modalTitle.textContent = "Edit User";
  usernameInput.value = user.username;
  passwordInput.value = ""; // leave empty, optional update
  roleInput.value = user.role;
  userModal.style.display = "block";
}

// Load all users
async function loadUsers() {
  try {
    const res = await fetch(`${API_BASE}/api/users`);
    const data = await res.json();

    usersTableBody.innerHTML = "";

    data.forEach((user) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${user.user_id}</td>
        <td>${user.username}</td>
        <td>${user.role}</td>
        <td>
          <button class="action-btn edit-btn">Edit</button>
          <button class="action-btn delete-btn">Delete</button>
        </td>
      `;

      // Edit button
      row
        .querySelector(".edit-btn")
        .addEventListener("click", () => editUser(user));
      // Delete button
      row
        .querySelector(".delete-btn")
        .addEventListener("click", () => deleteUser(user.user_id));

      usersTableBody.appendChild(row);
    });
  } catch (err) {
    userMessage.innerHTML = `<p style="color:red">Error loading users: ${err.message}</p>`;
  }
}

// Load users on page load
window.addEventListener("DOMContentLoaded", loadUsers);
