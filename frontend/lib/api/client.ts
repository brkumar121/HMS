const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

export type ApiHealth = {
  status: string;
  timestamp: string;
};

export async function getApiHealth(): Promise<ApiHealth> {
  const response = await fetch(`${API_BASE_URL}/api/public/health`, {
    cache: "no-store"
  });

  if (!response.ok) {
    throw new Error("API health check failed");
  }

  return response.json() as Promise<ApiHealth>;
}

async function apiRequest<T>(path: string, options?: RequestInit): Promise<T> {
  const token = typeof window !== "undefined" ? window.localStorage.getItem("hms_access_token") : null;
  const isForm = typeof FormData !== "undefined" && options?.body instanceof FormData;
  const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers: { ...(isForm ? {} : { "Content-Type": "application/json" }), ...(token ? { Authorization: `Bearer ${token}` } : {}), ...(options?.headers ?? {}) } });
  if (!response.ok) throw new Error(`API request failed: ${response.status}`);
  if (response.status === 204) return undefined as T;
  return response.json() as Promise<T>;
}
export type LoginPayload = { email: string; password: string };
export type AuthSession = { accessToken: string; tokenType: string; expiresIn: number; displayName: string; role: string };
export async function login(payload: LoginPayload): Promise<AuthSession> { const session = await apiRequest<AuthSession>("/api/auth/login", { method: "POST", body: JSON.stringify(payload) }); if (typeof window !== "undefined") window.localStorage.setItem("hms_access_token", session.accessToken); return session; }
export function logout(): void { if (typeof window !== "undefined") window.localStorage.removeItem("hms_access_token"); }
export function hasSession(): boolean { return typeof window !== "undefined" && Boolean(window.localStorage.getItem("hms_access_token")); }
export const getHospitalProfile = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/profile`);
export const getPublicDoctors = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/doctors`);
export const getPublicServices = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/services`);
export const getPublicContent = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/content/public`);
export const getPublicPages = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/pages`);
export const getPublicWebsiteSettings = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/website/settings`);
export const getDoctorSlots = (slug: string, doctorId: string, date: string) => apiRequest(`/api/public/hospitals/${slug}/doctors/${doctorId}/availability?date=${encodeURIComponent(date)}`);
export const createPublicAppointment = (slug: string, payload: unknown) => apiRequest(`/api/public/hospitals/${slug}/appointments`, { method: "POST", body: JSON.stringify(payload) });
export const getPublicAppointmentStatus = (slug: string, appointmentId: string, phone: string) => apiRequest(`/api/public/hospitals/${slug}/appointments/status?appointmentId=${encodeURIComponent(appointmentId)}&phone=${encodeURIComponent(phone)}`);
export const joinPublicWaitlist = (slug: string, payload: unknown) => apiRequest(`/api/public/hospitals/${slug}/waitlist`, { method: "POST", body: JSON.stringify(payload) });
export const listWaitlist = (slug: string) => apiRequest(`/api/hospitals/${slug}/waitlist`);
export const updateWaitlistStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/waitlist/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const listAppointments = (slug: string) => apiRequest(`/api/hospitals/${slug}/appointments`);
export const searchAppointments = (slug: string, query: string) => apiRequest(`/api/hospitals/${slug}/appointment-search?patientName=${encodeURIComponent(query)}`);
export const searchPatients = (slug: string, query: string) => apiRequest(`/api/hospitals/${slug}/patients?query=${encodeURIComponent(query)}`);
export const listFollowUps = (slug: string) => apiRequest(`/api/hospitals/${slug}/follow-ups`);
export const listQueue = (slug: string, date: string) => apiRequest(`/api/hospitals/${slug}/queue-board?date=${encodeURIComponent(date)}`);
export const listStaff = (slug: string) => apiRequest(`/api/hospitals/${slug}/staff`);
export const listBranches = (slug: string) => apiRequest(`/api/hospitals/${slug}/branches`);
export const listServices = (slug: string) => apiRequest(`/api/hospitals/${slug}/services`);
export const listDepartments = (slug: string) => apiRequest(`/api/hospitals/${slug}/departments`);
export const listDoctors = (slug: string) => apiRequest(`/api/hospitals/${slug}/doctors`);
export const getSubscription = (slug: string) => apiRequest(`/api/hospitals/${slug}/subscription`);
export const getPaymentSettings = (slug: string) => apiRequest(`/api/hospitals/${slug}/appointment-payments`);
export const listReminders = (slug: string) => apiRequest(`/api/hospitals/${slug}/reminders`);
export const listDoctorAvailability = (slug: string, doctorId: string) => apiRequest(`/api/hospitals/${slug}/doctors/${doctorId}/availability`);
export const listDoctorLeaves = (slug: string, doctorId: string) => apiRequest(`/api/hospitals/${slug}/doctors/${doctorId}/leave-periods`);
export const listNotifications = (slug: string) => apiRequest(`/api/hospitals/${slug}/notifications`);
export const listSocialConnections = (slug: string) => apiRequest(`/api/hospitals/${slug}/social/connections`);
export const createSocialConnection = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/social/connections`, { method: "POST", body: JSON.stringify(payload) });
export const updateSocialConnection = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/social/connections/${id}`, { method: "PATCH", body: JSON.stringify(payload) });
export const getAppointmentReport = (slug: string) => apiRequest(`/api/hospitals/${slug}/reports/appointments`);
export const getQueueReport = (slug: string) => apiRequest(`/api/hospitals/${slug}/reports/queue`);
export const getAuditLog = (slug: string) => apiRequest(`/api/hospitals/${slug}/reports/audit`);
export const createTenant = (payload: unknown) => apiRequest(`/api/platform/tenants`, { method: "POST", body: JSON.stringify(payload) });
export const listTenants = () => apiRequest(`/api/platform/tenants`);
export const inviteStaff = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/staff`, { method: "POST", body: JSON.stringify(payload) });
export const updateStaffStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/staff/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const createBranch = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/branches`, { method: "POST", body: JSON.stringify(payload) });
export const updateBranchStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/branches/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const listWebsitePages = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/pages`);
export const createService = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/services`, { method: "POST", body: JSON.stringify(payload) });
export const createDepartment = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/departments`, { method: "POST", body: JSON.stringify(payload) });
export const createDoctor = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/doctors`, { method: "POST", body: JSON.stringify(payload) });
export const createAvailability = (slug: string, doctorId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/doctors/${doctorId}/availability`, { method: "POST", body: JSON.stringify(payload) });
export const createLeavePeriod = (slug: string, doctorId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/doctors/${doctorId}/leave-periods`, { method: "POST", body: JSON.stringify(payload) });
export const updateAppointmentStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/appointments/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const rescheduleAppointment = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/appointments/${id}/reschedule`, { method: "PATCH", body: JSON.stringify(payload) });
export const updateAppointmentPayment = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/appointments/${id}/payment`, { method: "PATCH", body: JSON.stringify(payload) });
export const createQueueToken = (slug: string, appointmentId: string) => apiRequest(`/api/hospitals/${slug}/queue/appointments/${appointmentId}/token`, { method: "POST" });
export const prioritizeQueueToken = (slug: string, tokenId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/queue/${tokenId}/priority`, { method: "PATCH", body: JSON.stringify(payload) });
export const updateQueueTokenStatus = (slug: string, tokenId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/queue/${tokenId}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const queueNotification = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/notifications`, { method: "POST", body: JSON.stringify(payload) });
export const retryNotification = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/notifications/${id}/retry`, { method: "POST" });
export const updateNotificationStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/notifications/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const createReminder = (slug: string, appointmentId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/reminders/appointments/${appointmentId}`, { method: "POST", body: JSON.stringify(payload) });
export const createFollowUp = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/follow-ups`, { method: "POST", body: JSON.stringify(payload) });
export const updateFollowUpStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/follow-ups/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const syncSocialConnection = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/social/connections/${id}/sync`, { method: "POST" });
export const updatePaymentSettings = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/appointment-payments`, { method: "PUT", body: JSON.stringify(payload) });
export const updateSubscription = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/subscription`, { method: "PUT", body: JSON.stringify(payload) });
export const listSubscriptionInvoices = (slug: string) => apiRequest(`/api/hospitals/${slug}/subscription/invoices`);
export const createSubscriptionInvoice = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/subscription/invoices`, { method: "POST", body: JSON.stringify(payload) });
export const updateSubscriptionInvoiceStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/subscription/invoices/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const updatePatientIdentifiers = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/patient-identifiers`, { method: "PUT", body: JSON.stringify(payload) });
export const getPatientIdentifiers = (slug: string) => apiRequest(`/api/hospitals/${slug}/patient-identifiers`);
export const updatePatientCommunicationConsent = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/patients/${id}/communication-consent`, { method: "PUT", body: JSON.stringify(payload) });
export const getPatientPrivacyStatus = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/patients/${id}/privacy`);
export const anonymizePatient = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/patients/${id}/privacy`, { method: "DELETE" });
export const getWebsiteSettings = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/settings`);
export const listWebsiteThemeSnapshots = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/theme-snapshots`);
export const createWebsiteThemeSnapshot = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/theme-snapshots`, { method: "POST" });
export const restoreWebsiteThemeSnapshot = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/website/theme-snapshots/${id}/restore`, { method: "POST" });
export const updateWebsiteSettings = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/settings`, { method: "PUT", body: JSON.stringify(payload) });
export const listCustomDomains = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/domains`);
export const addCustomDomain = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/domains`, { method: "POST", body: JSON.stringify(payload) });
export const verifyCustomDomain = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/domains/${id}/verify`, { method: "POST", body: JSON.stringify(payload) });
export const disableCustomDomain = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/website/domains/${id}`, { method: "DELETE" });
export const listImportJobs = (slug: string) => apiRequest(`/api/hospitals/${slug}/imports`);
export const getNotificationProviderHealth = (slug: string) => apiRequest(`/api/hospitals/${slug}/notifications/provider-health`);
export const getSetupHealth = (slug: string) => apiRequest(`/api/hospitals/${slug}/setup-health`);
export const uploadImport = (slug: string, type: string, file: File) => { const body = new FormData(); body.append("type", type); body.append("file", file); return apiRequest(`/api/hospitals/${slug}/imports`, { method: "POST", body }); };
export const createWebsitePage = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/pages`, { method: "POST", body: JSON.stringify(payload) });
export const createWebsiteContent = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/content`, { method: "POST", body: JSON.stringify(payload) });
export const listWebsiteContent = (slug: string) => apiRequest(`/api/hospitals/${slug}/website/content`);
