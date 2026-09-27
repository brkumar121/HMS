"use client";
import { useReminders } from "../../../../lib/api/hooks";
export default function MobilePatientReminders(){const q=useReminders("city-care");const rows=(q.data as any[])||[];return <main className="min-h-screen bg-slate-50 p-6"><h1 className="text-2xl font-semibold">Care reminders</h1><section className="mt-6 grid gap-3">{rows.map(x=><article key={x.id} className="rounded-lg border bg-white p-4"><h2 className="font-semibold">{x.channel} reminder</h2><p className="mt-2 text-sm text-slate-600">{x.remindAt} · {x.status}</p></article>)}</section></main>}
