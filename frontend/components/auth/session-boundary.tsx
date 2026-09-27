"use client";
import { ReactNode, useEffect, useState } from "react"; import { hasSession } from "@/lib/api/client";
export function SessionBoundary({children}:{children:ReactNode}){const [ready,setReady]=useState(false);useEffect(()=>{if(!hasSession()){window.location.href="/login";return;}setReady(true);},[]);return ready?children:null;}
