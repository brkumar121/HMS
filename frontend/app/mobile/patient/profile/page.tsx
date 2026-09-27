"use client";
import { useHospitalProfile } from "../../../../lib/api/hooks";
export default function MobilePatientProfile(){const q=useHospitalProfile("city-care");const x=q.data as any;return <main className="min-h-screen bg-slate-50 p-6"><h1 className="text-2xl font-semibold">My profile</h1><article className="mt-6 rounded-lg border bg-white p-5"><h2 className="font-semibold">{x?.name||"Patient profile"}</h2><p className="mt-3 text-sm text-slate-600">Hospital contact profile and identifier preferences are available for your visit.</p></article></main>}
