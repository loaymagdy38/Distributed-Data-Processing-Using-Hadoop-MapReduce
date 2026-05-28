#!/usr/bin/env python3
"""
Dataset Generator for Task 2: Status-Based Partitioning
Generates orders.txt with format: order_id,status,amount,customer_id,order_date
Target size: ~1 GB
"""

import random
import os
from datetime import date, timedelta

# ─────────────────────────────────────────────
# Configuration
# ─────────────────────────────────────────────
OUTPUT_FILE    = "orders.txt"
TARGET_GB      = 1.1          # generate slightly over 1 GB to be safe
TARGET_BYTES   = int(TARGET_GB * 1024 * 1024 * 1024)

STATUSES       = ["Pending", "Completed", "Cancelled", "Processing"]
STATUS_WEIGHTS = [0.30, 0.40, 0.15, 0.15]   # realistic distribution

NUM_CUSTOMERS  = 50_000       # pool of customers

START_DATE     = date(2022, 1, 1)
END_DATE       = date(2024, 12, 31)

# Bad row injection rate (~1%)
BAD_ROW_RATE   = 0.01

# ─────────────────────────────────────────────
# Helpers
# ─────────────────────────────────────────────
def random_date(start, end):
    delta = (end - start).days
    return start + timedelta(days=random.randint(0, delta))

def customer_id(n):
    return f"C{n:05d}"

# ─────────────────────────────────────────────
# Generate file
# ─────────────────────────────────────────────
print(f"Generating {OUTPUT_FILE} (target: {TARGET_GB} GB) ...")

order_num   = 1
bytes_written = 0

with open(OUTPUT_FILE, "w", buffering=8 * 1024 * 1024) as f:   # 8 MB write buffer
    while bytes_written < TARGET_BYTES:
        roll = random.random()

        if roll < BAD_ROW_RATE * 0.33:
            # Bad: negative amount (validation test)
            oid    = f"O{order_num:010d}"
            status = random.choice(STATUSES)
            amount = -random.randint(1, 500)
            cid    = customer_id(random.randint(1, NUM_CUSTOMERS))
            d      = random_date(START_DATE, END_DATE)
            line   = f"{oid},{status},{amount},{cid},{d}\n"

        elif roll < BAD_ROW_RATE * 0.66:
            # Bad: missing fields
            line = f"O{order_num:010d},Pending\n"

        elif roll < BAD_ROW_RATE:
            # Bad: non-numeric amount
            oid    = f"O{order_num:010d}"
            status = random.choice(STATUSES)
            cid    = customer_id(random.randint(1, NUM_CUSTOMERS))
            d      = random_date(START_DATE, END_DATE)
            line   = f"{oid},{status},INVALID,{cid},{d}\n"

        else:
            # Normal row
            oid    = f"O{order_num:010d}"
            status = random.choices(STATUSES, weights=STATUS_WEIGHTS)[0]
            amount = round(random.uniform(10.0, 5000.0), 2)
            cid    = customer_id(random.randint(1, NUM_CUSTOMERS))
            d      = random_date(START_DATE, END_DATE)
            line   = f"{oid},{status},{amount},{cid},{d}\n"

        f.write(line)
        bytes_written += len(line.encode())
        order_num += 1

        if order_num % 1_000_000 == 0:
            gb_done = bytes_written / (1024**3)
            print(f"  {order_num:,} orders written ... {gb_done:.2f} GB")

final_gb = bytes_written / (1024**3)
print(f"\n✅ Done!")
print(f"   File : {OUTPUT_FILE}")
print(f"   Size : {final_gb:.2f} GB")
print(f"   Rows : {order_num - 1:,}")
