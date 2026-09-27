"use client";
import { LogOut } from "lucide-react"; import { logout } from "@/lib/api/client";
export function LogoutButton(){return <button onClick={()=>{logout();window.location.href="/login";}} className="inline-flex items-center gap-2 rounded-md border border-slate-300 px-3 py-2 text-sm font-semibold text-slate-700"><LogOut size={16}/>Sign out</button>}
