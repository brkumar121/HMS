package com.hms.notifications;
public interface NotificationProvider { NotificationChannel channel(); void send(NotificationMessage message); }
