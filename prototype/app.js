const roles = {
  platform: {
    label: "SaaS Platform Owner",
    action: "Add hospital",
    screens: [
      {
        id: "platform-dashboard",
        title: "Platform Dashboard",
        mobile: "Owner mobile",
        render: () => `
          ${stats([
            ["Hospitals", "42", "36 active, 4 trial, 2 overdue", "green"],
            ["Monthly ARR", "₹18.6L", "Up 14% this quarter", "blue"],
            ["Usage Alerts", "7", "Doctors, storage, SMS limits", "amber"],
            ["Support Flags", "3", "Tenant-safe access pending", "red"]
          ])}
          <div class="grid cols-2">
            ${panel("Subscribed Hospitals", hospitalTable())}
            ${panel("Plan Usage Watchlist", `
              ${listItem("CityCare Hospital", "92% doctor limit, 81% WhatsApp quota", "Upgrade suggested")}
              ${listItem("Nirmal Clinic Group", "Custom domain verification failed", "DNS action")}
              ${listItem("Westside Medical", "Payment overdue by 6 days", "Restrict publish soon")}
              ${listItem("Lotus Children Hospital", "Storage at 88%", "Review media")}
            `)}
          </div>
          ${panel("Tenant Lifecycle Controls", `
            <div class="button-row">
              <button class="primary-button">Create Tenant</button>
              <button class="secondary-button">Reactivate</button>
              <button class="secondary-button">Suspend</button>
              <button class="danger-button">Cancel Subscription</button>
            </div>
          `)}
        `
      },
      {
        id: "plans",
        title: "Plans And Feature Flags",
        mobile: "Plans",
        render: () => `
          <div class="grid cols-3">
            ${plan("Starter", "₹4,999/mo", ["5 doctors", "10 staff", "1 branch", "Website template", "Email alerts"])}
            ${plan("Growth", "₹12,999/mo", ["25 doctors", "50 staff", "3 branches", "Custom domain", "SMS/WhatsApp"])}
            ${plan("Enterprise", "Custom", ["Unlimited branches", "Priority support", "Audit exports", "Advanced analytics"])}
          </div>
          ${panel("Feature Flags", `
            ${toggleRow("Queue token display", "Enabled for Growth and Enterprise")}
            ${toggleRow("Token prioritization audit", "Enabled for all plans")}
            ${toggleRow("Social feed embeds", "Growth beta")}
            ${toggleRow("Patient login", "Disabled for MVP")}
            ${toggleRow("Multi-branch routing", "Enterprise")}
          `)}
        `
      },
      {
        id: "support-audit",
        title: "Support And Audit",
        mobile: "Audit",
        render: () => `
          <div class="grid cols-2">
            ${panel("Tenant-Safe Support Access", `
              <label class="field-label">Hospital</label><select class="select"><option>CityCare Hospital</option></select>
              <label class="field-label">Reason</label><input class="input" value="Investigate website publish failure" />
              <label class="field-label">Access Scope</label><select class="select"><option>Settings and logs only</option><option>Appointments masked</option></select>
              <div class="button-row"><button class="primary-button">Request Access</button><button class="secondary-button">View Policy</button></div>
            `)}
            ${panel("Recent Audit Events", timeline([
              ["12:09", "Plan upgraded for CityCare Hospital by Platform Owner"],
              ["11:42", "Support access requested for domain setup"],
              ["10:15", "Tenant suspended for overdue billing: Westside Medical"],
              ["09:50", "Feature flag enabled: queue-token-display"]
            ]))}
          </div>
        `
      }
    ]
  },
  admin: {
    label: "Hospital Owner / Admin",
    action: "Publish website",
    screens: [
      {
        id: "admin-home",
        title: "Hospital Command Center",
        mobile: "Admin mobile",
        render: () => `
          ${stats([
            ["Setup Health", "86%", "Logo, doctors, slots ready", "green"],
            ["Appointments Today", "128", "22 pending confirmation", "blue"],
            ["Website Status", "Draft", "3 unpublished changes", "amber"],
            ["Social Referrals", "41", "Instagram and WhatsApp lead", "violet"]
          ])}
          <div class="grid cols-2">
            ${panel("Setup Checklist", `
              ${check("Hospital profile", true)}
              ${check("Logo and theme", true)}
              ${check("Departments and doctors", true)}
              ${check("Doctor availability", false)}
              ${check("Social handles", true)}
              ${check("Custom domain", false)}
            `)}
            ${panel("Operational Snapshot", `
              ${listItem("Cardiology", "34 appointments, 4 priority tokens", "Busy")}
              ${listItem("Pediatrics", "18 appointments, 2 walk-ins", "On track")}
              ${listItem("Orthopedics", "12 appointments, 1 doctor on leave", "Review")}
              ${listItem("General Medicine", "42 appointments, 9 waiting", "Active")}
            `)}
          </div>
        `
      },
      {
        id: "hospital-profile",
        title: "Hospital Profile And Branches",
        mobile: "Profile",
        render: () => `
          <div class="grid cols-2">
            ${panel("Hospital Identity", `
              <div class="form-grid">
                ${field("Hospital name", "CityCare Multispecialty")}
                ${field("Legal name", "CityCare Health Pvt Ltd")}
                ${field("Main phone", "+91 98765 43210")}
                ${field("Emergency phone", "+91 98765 00000")}
                ${field("Email", "care@citycare.example")}
                ${field("Business hours", "Mon-Sat 08:00-20:00")}
              </div>
              <label class="field-label">Address</label><textarea class="textarea">MG Road, Bengaluru, Karnataka</textarea>
            `)}
            ${panel("Branches", `
              ${listItem("Main Hospital", "MG Road, 34 doctors, full services", "Primary")}
              ${listItem("North Clinic", "Hebbal, 8 doctors, OPD only", "Active")}
              ${listItem("South Diagnostic Center", "JP Nagar, labs and scans", "Draft")}
              <button class="secondary-button">Add Branch</button>
            `)}
          </div>
        `
      },
      {
        id: "doctors-departments",
        title: "Doctors, Departments, Availability",
        mobile: "Doctors",
        render: () => `
          <div class="grid cols-2">
            ${panel("Department Setup", `
              ${listItem("Cardiology", "6 doctors, 4 services, visible on website", "Live")}
              ${listItem("Pediatrics", "4 doctors, child priority reason enabled", "Live")}
              ${listItem("Orthopedics", "3 doctors, Saturday sessions", "Live")}
              ${listItem("Dermatology", "2 doctors, no appointment slots", "Needs slots")}
            `)}
            ${panel("Doctor Availability Editor", `
              <div class="form-grid">
                ${field("Doctor", "Dr. Asha Mehta")}
                ${field("Department", "Cardiology")}
                ${field("Slot duration", "15 minutes")}
                ${field("Room", "C-204")}
                ${field("Session", "Morning OPD")}
                ${field("Leave", "None")}
              </div>
              <div class="chip-row"><span class="chip">Mon 09-13</span><span class="chip">Wed 09-13</span><span class="chip">Fri 14-18</span></div>
            `)}
          </div>
        `
      },
      {
        id: "theme-builder",
        title: "Website Theme Builder",
        mobile: "Theme",
        render: () => `
          <div class="grid cols-2">
            ${panel("Theme Controls", `
              <label class="field-label">Template</label><select class="select"><option>Modern Care</option><option>Specialty Clinic</option><option>Multi-Branch Hospital</option></select>
              <label class="field-label">Logo</label><input class="input" value="citycare-logo.png" />
              <label class="field-label">Brand colors</label>
              <div class="button-row"><span class="color-swatch swatch-teal"></span><span class="color-swatch swatch-blue"></span><span class="color-swatch swatch-green"></span><span class="color-swatch swatch-violet"></span></div>
              <label class="field-label">Hero layout</label><select class="select"><option>Image with appointment CTA</option><option>Doctor search first</option><option>Emergency contact first</option></select>
              <div class="button-row"><button class="primary-button">Preview</button><button class="secondary-button">Save Draft</button><button class="primary-button">Publish</button></div>
            `)}
            ${sitePreview()}
          </div>
        `
      },
      {
        id: "token-settings",
        title: "Token Rules And Priority Reasons",
        mobile: "Tokens",
        render: () => `
          <div class="grid cols-2">
            ${panel("Queue Token Configuration", `
              ${toggleRow("Enable queue tokens", "For confirmed appointments, walk-ins, and check-ins")}
              ${toggleRow("Patient display board", "Show current token and waiting position")}
              <label class="field-label">Token uniqueness</label><select class="select"><option>Doctor + Date + Session</option><option>Department + Date</option><option>Branch + Date</option></select>
              <label class="field-label">Priority behavior</label><select class="select"><option>Insert after current consultation</option><option>Move to front</option><option>Move up by configured count</option></select>
            `)}
            ${panel("Configurable Priority Reasons", `
              ${check("Emergency case", true)}
              ${check("Senior citizen", true)}
              ${check("Disability assistance", true)}
              ${check("Child patient", true)}
              ${check("Pregnancy", true)}
              ${check("Doctor request", true)}
              ${check("Operational adjustment with note", true)}
              <button class="secondary-button">Add Reason</button>
            `)}
          </div>
        `
      },
      {
        id: "reports",
        title: "Reports And Analytics",
        mobile: "Reports",
        render: () => `
          ${stats([
            ["Appointment Sources", "38%", "Website leads", "blue"],
            ["No-show Rate", "6.8%", "Down 1.2%", "green"],
            ["Priority Tokens", "19", "Emergency reason leads", "amber"],
            ["Content Views", "12.4K", "Top: Cardiology page", "violet"]
          ])}
          <div class="grid cols-2">
            ${panel("Token Prioritization Report", tokenReport())}
            ${panel("Audit Trail", timeline([
              ["12:20", "Token C-018 prioritized: emergency case"],
              ["11:55", "Website theme published by Hospital Admin"],
              ["11:30", "Doctor availability changed for Dr. Asha"],
              ["10:02", "Priority reason added: pregnancy"]
            ]))}
          </div>
        `
      }
    ]
  },
  reception: {
    label: "Reception / Front Desk",
    action: "Create appointment",
    screens: [
      {
        id: "front-desk",
        title: "Front Desk Dashboard",
        mobile: "Desk",
        render: () => `
          ${stats([
            ["Waiting", "28", "9 in General Medicine", "amber"],
            ["Pending Requests", "22", "Website and WhatsApp", "blue"],
            ["Checked In", "76", "Across 12 doctors", "green"],
            ["Priority Tokens", "6", "All reasons captured", "red"]
          ])}
          ${panel("Appointment Workboard", kanban())}
        `
      },
      {
        id: "create-appointment",
        title: "Create Or Confirm Appointment",
        mobile: "Book",
        render: () => `
          <div class="grid cols-2">
            ${panel("Patient And Appointment", `
              <div class="form-grid">
                ${field("Phone", "+91 99887 77665")}
                ${field("Patient", "Kiran Rao")}
                ${field("Age / DOB", "42")}
                ${field("Department", "Cardiology")}
                ${field("Doctor", "Dr. Asha Mehta")}
                ${field("Source", "Phone call")}
                ${field("Date", "2026-09-19")}
                ${field("Slot", "10:45")}
              </div>
              <label class="field-label">Reason for visit</label><textarea class="textarea">Chest discomfort during morning walk</textarea>
              <div class="button-row"><button class="primary-button">Confirm Appointment</button><button class="secondary-button">Find Existing Patient</button></div>
            `)}
            ${panel("Available Slots And Conflicts", `
              ${listItem("10:30", "Booked: Priya Nair", "Conflict")}
              ${listItem("10:45", "Available, room C-204", "Recommended")}
              ${listItem("11:00", "Available, room C-204", "Open")}
              ${listItem("11:15", "Held for emergency buffer", "Hold")}
            `)}
          </div>
        `
      },
      {
        id: "queue-board",
        title: "Queue And Token Board",
        mobile: "Queue",
        render: () => `
          <div class="grid cols-2">
            ${panel("Cardiology Queue", queueRows())}
            ${panel("Prioritize Token", `
              <label class="field-label">Token</label><select class="select"><option>C-018 | Kiran Rao</option><option>C-021 | Walk-in</option></select>
              <label class="field-label">Reason</label><select class="select"><option>Emergency case</option><option>Senior citizen</option><option>Disability assistance</option><option>Doctor request</option><option>Operational adjustment</option></select>
              <label class="field-label">Note</label><textarea class="textarea">Chest discomfort, approved by duty doctor.</textarea>
              <div class="button-row"><button class="primary-button">Prioritize Token</button><button class="secondary-button">Hold</button><button class="secondary-button">Skip</button><button class="danger-button">No-show</button></div>
              <p class="meta">Audit captures previous position, new position, reason, note, user, timestamp, doctor, and appointment reference.</p>
            `)}
          </div>
        `
      },
      {
        id: "requests",
        title: "Website And Social Requests",
        mobile: "Requests",
        render: () => `
          ${panel("Pending Appointment Requests", `
            <div class="table-wrap">
              <table>
                <thead><tr><th>Patient</th><th>Source</th><th>Need</th><th>Preferred</th><th>Action</th></tr></thead>
                <tbody>
                  <tr><td>Neha Shah<br><span class="meta">+91 90000 11111</span></td><td>Website</td><td>Pediatrics</td><td>Today 16:00</td><td><button class="secondary-button">Confirm</button></td></tr>
                  <tr><td>Arun Das<br><span class="meta">+91 90000 22222</span></td><td>WhatsApp</td><td>Orthopedics</td><td>Tomorrow morning</td><td><button class="secondary-button">Call back</button></td></tr>
                  <tr><td>Mary Joseph<br><span class="meta">+91 90000 33333</span></td><td>Instagram</td><td>Dermatology</td><td>Any weekend</td><td><button class="secondary-button">Suggest slots</button></td></tr>
                </tbody>
              </table>
            </div>
          `)}
        `
      }
    ]
  },
  doctor: {
    label: "Doctor",
    action: "Call next",
    screens: [
      {
        id: "doctor-today",
        title: "Doctor Schedule And Queue",
        mobile: "Doctor",
        render: () => `
          ${stats([
            ["Next Token", "C-018", "Emergency case", "red"],
            ["Waiting", "11", "Estimated 42 min", "amber"],
            ["Completed", "17", "Since 09:00", "green"],
            ["No-shows", "2", "Follow-up pending", "blue"]
          ])}
          <div class="grid cols-2">
            ${panel("Current Queue", queueRows())}
            ${panel("Current Patient Context", `
              <h3>Kiran Rao</h3>
              <p class="muted">42 years, Cardiology, token C-018</p>
              ${listItem("Reason", "Chest discomfort during morning walk", "Priority")}
              ${listItem("Staff note", "Duty doctor approved priority after triage", "Internal")}
              ${listItem("Appointment source", "Phone call", "Confirmed")}
              <div class="button-row"><button class="primary-button">Start Consultation</button><button class="secondary-button">Complete</button><button class="secondary-button">No-show</button></div>
            `)}
          </div>
        `
      },
      {
        id: "doctor-filter",
        title: "Appointments By Date And Status",
        mobile: "Filter",
        render: () => `
          ${panel("Doctor Appointment List", appointmentTable())}
        `
      }
    ]
  },
  content: {
    label: "Content / Marketing Staff",
    action: "Create content",
    screens: [
      {
        id: "content-dashboard",
        title: "Website Content Dashboard",
        mobile: "Content",
        render: () => `
          ${stats([
            ["Published Items", "84", "12 featured", "green"],
            ["Drafts", "9", "3 need review", "amber"],
            ["Social Cards", "18", "Instagram and YouTube", "violet"],
            ["Top Page", "Cardiology", "3.8K views", "blue"]
          ])}
          <div class="grid cols-2">
            ${panel("Categorized Content", `
              ${listItem("Services", "24 items, 8 featured on home", "Live")}
              ${listItem("Health Articles", "31 items, SEO ready", "Live")}
              ${listItem("Announcements", "6 items, 2 scheduled", "Drafts")}
              ${listItem("Gallery", "23 images, 4 videos", "Live")}
            `)}
            ${panel("Social Media Handles", `
              ${field("Facebook", "facebook.com/citycare")}
              ${field("Instagram", "instagram.com/citycare")}
              ${field("YouTube", "youtube.com/@citycare")}
              ${field("WhatsApp", "+91 98765 43210")}
            `)}
          </div>
        `
      },
      {
        id: "content-editor",
        title: "Content Editor And Social Card",
        mobile: "Editor",
        render: () => `
          <div class="grid cols-2">
            ${panel("Article Details", `
              <div class="form-grid">
                ${field("Title", "When to visit a cardiologist")}
                ${field("Category", "Health Articles")}
                ${field("Status", "Draft")}
                ${field("Author", "Dr. Asha Mehta")}
                ${field("SEO title", "Cardiology care in Bengaluru")}
                ${field("Social image", "cardiology-care.jpg")}
              </div>
              <label class="field-label">Summary</label><textarea class="textarea">A patient-friendly guide for recognizing cardiac warning signs.</textarea>
              <div class="button-row"><button class="primary-button">Preview</button><button class="secondary-button">Save Draft</button><button class="primary-button">Publish</button></div>
            `)}
            ${panel("Manual Social Card", `
              ${field("Platform", "Instagram")}
              ${field("Post URL", "https://instagram.com/p/example")}
              ${field("Display category", "Cardiology")}
              <p class="muted">Use this when direct feeds are unavailable, blocked, or expired.</p>
            `)}
          </div>
        `
      }
    ]
  },
  patient: {
    label: "Patient / Public Website",
    action: "Book appointment",
    screens: [
      {
        id: "website-home",
        title: "Hospital Website Home",
        mobile: "Website",
        render: () => sitePreview(true)
      },
      {
        id: "find-doctor",
        title: "Find Doctor And Book",
        mobile: "Book",
        render: () => `
          <div class="grid cols-2">
            ${panel("Search Doctors", `
              <div class="form-grid">
                ${field("Search", "heart pain")}
                ${field("Department", "Cardiology")}
                ${field("Language", "English, Hindi")}
                ${field("Availability", "Today")}
              </div>
              ${doctorCard("Dr. Asha Mehta", "Cardiology", "18 yrs exp", "Today 10:45, 11:00, 11:30")}
              ${doctorCard("Dr. Rohan Iyer", "Cardiology", "11 yrs exp", "Tomorrow 09:30, 10:15")}
            `)}
            ${panel("Book Appointment", `
              <div class="form-grid">
                ${field("Patient name", "Kiran Rao")}
                ${field("Phone", "+91 99887 77665")}
                ${field("Preferred slot", "Today 10:45")}
                ${field("Visit type", "First visit")}
              </div>
              <label class="field-label">Reason</label><textarea class="textarea">Chest discomfort during morning walk</textarea>
              <p class="meta">Your request will be confirmed by CityCare. Emergency cases should call +91 98765 00000.</p>
              <button class="primary-button">Request Appointment</button>
            `)}
          </div>
        `
      },
      {
        id: "patient-token",
        title: "Patient Token Status",
        mobile: "Token",
        render: () => `
          <div class="grid cols-2">
            ${panel("Token Display", `
              <div class="grid cols-3">
                <div class="stat"><span>Your token</span><strong>C-018</strong><span class="badge amber">Priority</span></div>
                <div class="stat"><span>Now serving</span><strong>C-014</strong><span class="meta">Cardiology</span></div>
                <div class="stat"><span>Estimated wait</span><strong>18m</strong><span class="meta">May change</span></div>
              </div>
              <p class="muted">Only token numbers are shown publicly. Patient names and private details are hidden.</p>
            `)}
            ${panel("Next Steps", `
              ${listItem("Arrive at reception", "Show appointment confirmation SMS or WhatsApp", "Required")}
              ${listItem("Doctor queue", "Watch the token display board", "In progress")}
              ${listItem("Need help?", "Call or WhatsApp CityCare front desk", "Contact")}
            `)}
          </div>
        `
      }
    ]
  }
};

let currentRole = "platform";
let currentScreen = roles[currentRole].screens[0].id;

const roleSelect = document.getElementById("roleSelect");
const screenNav = document.getElementById("screenNav");
const screenTitle = document.getElementById("screenTitle");
const screenContent = document.getElementById("screenContent");
const eyebrow = document.getElementById("eyebrow");
const primaryAction = document.getElementById("primaryAction");
const mobileTitle = document.getElementById("mobileTitle");
const mobileContent = document.getElementById("mobileContent");

Object.entries(roles).forEach(([id, role]) => {
  const option = document.createElement("option");
  option.value = id;
  option.textContent = role.label;
  roleSelect.appendChild(option);
});

roleSelect.addEventListener("change", () => {
  currentRole = roleSelect.value;
  currentScreen = roles[currentRole].screens[0].id;
  render();
});

function render() {
  const role = roles[currentRole];
  const screen = role.screens.find((item) => item.id === currentScreen) || role.screens[0];
  eyebrow.textContent = role.label;
  screenTitle.textContent = screen.title;
  primaryAction.textContent = role.action;
  mobileTitle.textContent = screen.mobile;
  screenContent.innerHTML = screen.render();
  mobileContent.innerHTML = mobileFor(screen.title);

  screenNav.innerHTML = "";
  role.screens.forEach((item) => {
    const button = document.createElement("button");
    button.className = `nav-button ${item.id === screen.id ? "active" : ""}`;
    button.textContent = item.title;
    button.addEventListener("click", () => {
      currentScreen = item.id;
      render();
    });
    screenNav.appendChild(button);
  });
}

function stats(items) {
  return `<div class="grid cols-4">${items.map(([label, value, text, tone]) => `
    <div class="panel stat">
      <span>${label}</span>
      <strong>${value}</strong>
      <span class="badge ${tone}">${text}</span>
    </div>
  `).join("")}</div>`;
}

function panel(title, body) {
  return `<section class="panel"><div class="panel-header"><h2>${title}</h2></div>${body}</section>`;
}

function listItem(title, text, badge) {
  return `<div class="list-item card-mini"><div><strong>${title}</strong><div class="meta">${text}</div></div><span class="badge">${badge}</span></div>`;
}

function field(label, value) {
  return `<label><span class="field-label">${label}</span><input class="input" value="${value}" /></label>`;
}

function check(label, done) {
  return `<div class="list-item card-mini"><span>${done ? "✓" : "○"} ${label}</span><span class="badge ${done ? "green" : "amber"}">${done ? "Done" : "Needs action"}</span></div>`;
}

function toggleRow(label, text) {
  return `<div class="list-item card-mini"><div><strong>${label}</strong><div class="meta">${text}</div></div><input type="checkbox" checked aria-label="${label}" /></div>`;
}

function plan(name, price, features) {
  return panel(name, `<strong class="muted">${price}</strong>${features.map((item) => `<div class="card-mini">✓ ${item}</div>`).join("")}<button class="secondary-button">Edit Plan</button>`);
}

function hospitalTable() {
  return `<div class="table-wrap"><table><thead><tr><th>Hospital</th><th>Plan</th><th>Status</th><th>Usage</th></tr></thead><tbody>
    <tr><td>CityCare Hospital</td><td>Growth</td><td><span class="badge green">Active</span></td><td>82%</td></tr>
    <tr><td>Nirmal Clinic Group</td><td>Enterprise</td><td><span class="badge amber">DNS pending</span></td><td>49%</td></tr>
    <tr><td>Westside Medical</td><td>Starter</td><td><span class="badge red">Overdue</span></td><td>67%</td></tr>
    <tr><td>Lotus Children Hospital</td><td>Growth</td><td><span class="badge green">Active</span></td><td>88%</td></tr>
  </tbody></table></div>`;
}

function tokenReport() {
  return `<div class="table-wrap"><table><thead><tr><th>Reason</th><th>Count</th><th>Top Department</th><th>Review</th></tr></thead><tbody>
    <tr><td>Emergency case</td><td>8</td><td>Cardiology</td><td><span class="badge red">High</span></td></tr>
    <tr><td>Senior citizen</td><td>5</td><td>General Medicine</td><td><span class="badge green">Normal</span></td></tr>
    <tr><td>Doctor request</td><td>4</td><td>Pediatrics</td><td><span class="badge amber">Check notes</span></td></tr>
    <tr><td>Operational adjustment</td><td>2</td><td>Orthopedics</td><td><span class="badge amber">Audit</span></td></tr>
  </tbody></table></div>`;
}

function timeline(items) {
  return `<div class="timeline">${items.map(([time, text]) => `<div class="timeline-item"><strong>${time}</strong><div class="meta">${text}</div></div>`).join("")}</div>`;
}

function kanban() {
  const lanes = [
    ["Requested", ["Neha Shah - Pediatrics", "Arun Das - Orthopedics"]],
    ["Confirmed", ["Kiran Rao - Cardiology", "Mary Joseph - Dermatology"]],
    ["Checked In", ["C-014 General", "C-018 Cardiology priority"]],
    ["Completed", ["C-009 Cardiology", "P-012 Pediatrics"]]
  ];
  return `<div class="kanban">${lanes.map(([name, cards]) => `<div class="lane"><strong>${name}</strong>${cards.map((card) => `<div class="card-mini">${card}</div>`).join("")}</div>`).join("")}</div>`;
}

function queueRows() {
  const rows = [
    ["C-014", "Meera S.", "In consultation", "green", false],
    ["C-018", "Kiran R.", "Priority: emergency case", "amber", true],
    ["C-015", "Walk-in", "Waiting", "blue", false],
    ["C-016", "Anil P.", "Held: lab report", "violet", false],
    ["C-017", "Patient hidden", "Waiting", "blue", false]
  ];
  return rows.map(([token, patient, status, tone, priority]) => `
    <div class="queue-row">
      <div class="button-row"><div class="token ${priority ? "priority" : ""}">${token}</div><div><strong>${patient}</strong><div class="meta">${status}</div></div></div>
      <span class="badge ${tone}">${priority ? "Priority" : "Normal"}</span>
    </div>
  `).join("");
}

function appointmentTable() {
  return `<div class="table-wrap"><table><thead><tr><th>Time</th><th>Token</th><th>Patient</th><th>Status</th><th>Reason</th></tr></thead><tbody>
    <tr><td>10:30</td><td>C-014</td><td>Meera S.</td><td><span class="badge green">In consultation</span></td><td>Follow-up</td></tr>
    <tr><td>10:45</td><td>C-018</td><td>Kiran R.</td><td><span class="badge amber">Priority</span></td><td>Chest discomfort</td></tr>
    <tr><td>11:00</td><td>C-015</td><td>Walk-in</td><td><span class="badge blue">Waiting</span></td><td>General check</td></tr>
    <tr><td>11:15</td><td>C-016</td><td>Anil P.</td><td><span class="badge violet">Held</span></td><td>Report pending</td></tr>
  </tbody></table></div>`;
}

function sitePreview(full = false) {
  return `<div class="site-preview">
    <div class="site-header"><strong>CityCare</strong><div class="button-row"><span>Doctors</span><span>Services</span><span>Articles</span><button class="primary-button">Book</button></div></div>
    <div class="site-hero"><h2>CityCare Multispecialty Hospital</h2><p>Find doctors, book appointments, and connect through WhatsApp or social channels.</p><button class="primary-button">Book Appointment</button></div>
    <div class="site-body">
      <div class="grid cols-3">
        ${doctorCard("Cardiology", "6 doctors", "Today slots", "Book")}
        ${doctorCard("Pediatrics", "4 doctors", "Child priority supported", "Book")}
        ${doctorCard("Health Articles", "31 posts", "Categorized content", "Read")}
      </div>
      ${full ? panel("Social And Contact", `<div class="button-row"><button class="secondary-button">WhatsApp</button><button class="secondary-button">Instagram</button><button class="secondary-button">YouTube</button><button class="secondary-button">Map</button></div>`) : ""}
    </div>
  </div>`;
}

function doctorCard(name, dept, exp, slots) {
  return `<div class="card-mini"><strong>${name}</strong><div class="meta">${dept} · ${exp}</div><p class="muted">${slots}</p><button class="secondary-button">Select</button></div>`;
}

function mobileFor(title) {
  return `
    <div class="mobile-card"><strong>${title}</strong><p class="meta">Role-based mobile layout with the same permissions and data boundaries.</p></div>
    <div class="mobile-card"><span class="badge green">Today</span><h3>Primary action</h3><p class="muted">${roles[currentRole].action} from mobile with compact forms and clear next steps.</p><button class="primary-button">${roles[currentRole].action}</button></div>
    <div class="mobile-card">${queueRows().split("</div>").slice(0, 6).join("</div>")}</div>
    <div class="mobile-card"><strong>Quick actions</strong><div class="button-row"><button class="secondary-button">Call</button><button class="secondary-button">WhatsApp</button><button class="secondary-button">Map</button></div></div>
  `;
}

render();
