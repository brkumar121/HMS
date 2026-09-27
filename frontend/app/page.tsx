import { RoleShell } from "@/components/admin/role-shell";
import { getApiHealth } from "@/lib/api/client";

export default async function Home() {
  let apiStatus = "not checked";

  try {
    const health = await getApiHealth();
    apiStatus = health.status;
  } catch {
    apiStatus = "offline";
  }

  return (
    <main className="min-h-screen">
      <header className="border-b border-slate-200 bg-white">
        <div className="mx-auto flex max-w-7xl flex-col gap-4 px-6 py-6 md:flex-row md:items-center md:justify-between">
          <div>
            <p className="text-sm font-medium uppercase text-clinical">Hospital SaaS</p>
            <h1 className="mt-1 text-2xl font-semibold text-ink">Appointment Management Platform</h1>
          </div>
          <div className="rounded-md border border-slate-200 px-3 py-2 text-sm text-slate-700">
            API status: <span className="font-semibold text-clinical">{apiStatus}</span>
          </div>
        </div>
      </header>

      <div className="mx-auto max-w-7xl px-6 py-8">
        <div className="mb-6 grid gap-4 lg:grid-cols-[1.5fr_1fr]">
          <section>
            <h2 className="text-xl font-semibold text-ink">Launch Foundation</h2>
            <p className="mt-2 max-w-3xl text-sm leading-6 text-slate-600">
              This shell starts the full-launch product structure: role-based admin surfaces,
              public website readiness, tenant-safe backend APIs, and implementation chunks
              that can expand without rewriting the foundation.
            </p>
          </section>
          <section className="rounded-lg border border-slate-200 bg-white p-4 shadow-sm">
            <h2 className="text-base font-semibold text-ink">Current Chunk</h2>
            <p className="mt-2 text-sm leading-6 text-slate-600">
              Project foundation, backend health, tenant model, and frontend role shell.
            </p>
          </section>
        </div>

        <RoleShell />
      </div>
    </main>
  );
}
