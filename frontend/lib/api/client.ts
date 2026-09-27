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
