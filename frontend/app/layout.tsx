import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "HMS SaaS",
  description: "Hospital appointment management SaaS and configurable public websites."
};

export default function RootLayout({
  children
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
