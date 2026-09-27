import { Activity, CalendarDays, Globe2, ShieldCheck, UsersRound } from "lucide-react";

const roleCards = [
  {
    title: "Platform Owner",
    detail: "Tenants, subscriptions, billing state, support access, provider health.",
    icon: ShieldCheck
  },
  {
    title: "Hospital Admin",
    detail: "Branches, doctors, departments, schedules, users, appointment rules.",
    icon: UsersRound
  },
  {
    title: "Reception",
    detail: "Requests, confirmations, check-in, queues, tokens, payment verification.",
    icon: CalendarDays
  },
  {
    title: "Doctor",
    detail: "Daily schedule, current queue, visit status, follow-up creation.",
    icon: Activity
  },
  {
    title: "Content Team",
    detail: "Website pages, themes, media, social cards, preview and publish.",
    icon: Globe2
  }
];

export function RoleShell() {
  return (
    <section className="grid gap-4 md:grid-cols-2 xl:grid-cols-5">
      {roleCards.map((role) => {
        const Icon = role.icon;
        return (
          <article key={role.title} className="rounded-lg border border-slate-200 bg-white p-4 shadow-sm">
            <div className="mb-3 flex h-10 w-10 items-center justify-center rounded-md bg-teal-50 text-clinical">
              <Icon aria-hidden="true" size={20} />
            </div>
            <h2 className="text-base font-semibold text-ink">{role.title}</h2>
            <p className="mt-2 text-sm leading-6 text-slate-600">{role.detail}</p>
          </article>
        );
      })}
    </section>
  );
}
