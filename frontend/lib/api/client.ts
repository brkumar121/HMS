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
  const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers: { "Content-Type": "application/json", ...(options?.headers ?? {}) } });
  if (!response.ok) throw new Error(`API request failed: ${response.status}`);
  return response.json() as Promise<T>;
}
export const getHospitalProfile = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/profile`);
export const getPublicDoctors = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/doctors`);
export const getPublicServices = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/services`);
export const getPublicContent = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/content`);
export const getPublicPages = (slug: string) => apiRequest(`/api/public/hospitals/${slug}/pages`);
export const getDoctorSlots = (slug: string, doctorId: string, date: string) => apiRequest(`/api/public/hospitals/${slug}/doctors/${doctorId}/availability?date=${encodeURIComponent(date)}`);
export const createPublicAppointment = (slug: string, payload: unknown) => apiRequest(`/api/public/hospitals/${slug}/appointments`, { method: "POST", body: JSON.stringify(payload) });
export const listAppointments = (slug: string) => apiRequest(`/api/hospitals/${slug}/appointments`);
export const searchAppointments = (slug: string, query: string) => apiRequest(`/api/hospitals/${slug}/appointment-search?patientName=${encodeURIComponent(query)}`);
export const searchPatients = (slug: string, query: string) => apiRequest(`/api/hospitals/${slug}/patients?query=${encodeURIComponent(query)}`);
export const listQueue = (slug: string, date: string) => apiRequest(`/api/hospitals/${slug}/queue-board?date=${encodeURIComponent(date)}`);
export const listStaff = (slug: string) => apiRequest(`/api/hospitals/${slug}/staff`);
export const listBranches = (slug: string) => apiRequest(`/api/hospitals/${slug}/branches`);
export const listServices = (slug: string) => apiRequest(`/api/hospitals/${slug}/services`);
export const getSubscription = (slug: string) => apiRequest(`/api/hospitals/${slug}/subscription`);
export const getPaymentSettings = (slug: string) => apiRequest(`/api/hospitals/${slug}/appointment-payments`);
export const listReminders = (slug: string) => apiRequest(`/api/hospitals/${slug}/reminders`);
export const listNotifications = (slug: string) => apiRequest(`/api/hospitals/${slug}/notifications`);
export const listSocialConnections = (slug: string) => apiRequest(`/api/hospitals/${slug}/social/connections`);
export const getAppointmentReport = (slug: string) => apiRequest(`/api/hospitals/${slug}/reports/appointments`);
export const getAuditLog = (slug: string) => apiRequest(`/api/hospitals/${slug}/reports/audit`);
export const createTenant = (payload: unknown) => apiRequest(`/api/platform/tenants`, { method: "POST", body: JSON.stringify(payload) });
export const inviteStaff = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/staff`, { method: "POST", body: JSON.stringify(payload) });
export const updateStaffStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/staff/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
export const createBranch = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/branches`, { method: "POST", body: JSON.stringify(payload) });
export const updateBranchStatus = (slug: string, id: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/branches/${id}/status`, { method: "PATCH", body: JSON.stringify(payload) });
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
export const createReminder = (slug: string, appointmentId: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/reminders/appointments/${appointmentId}`, { method: "POST", body: JSON.stringify(payload) });
export const syncSocialConnection = (slug: string, id: string) => apiRequest(`/api/hospitals/${slug}/social/connections/${id}/sync`, { method: "POST" });
export const updatePaymentSettings = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/appointment-payments`, { method: "PUT", body: JSON.stringify(payload) });
export const updateSubscription = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/subscription`, { method: "PUT", body: JSON.stringify(payload) });
export const updatePatientIdentifiers = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/patient-identifiers`, { method: "PUT", body: JSON.stringify(payload) });
export const createWebsitePage = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/pages`, { method: "POST", body: JSON.stringify(payload) });
export const createWebsiteContent = (slug: string, payload: unknown) => apiRequest(`/api/hospitals/${slug}/website/content`, { method: "POST", body: JSON.stringify(payload) });
