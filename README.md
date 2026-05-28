# Distributed Data Processing using Hadoop MapReduce

A collection of distributed data processing projects built using Hadoop MapReduce to handle large-scale datasets efficiently.

The projects demonstrate the implementation of:
- Distributed Cache
- Reduce-Side Joins
- Custom Partitioners
- Parallel Data Processing
- Large-scale analytics pipelines

using custom Mapper, Reducer, Driver, and Partitioner classes.

---

# Project Overview

This repository contains multiple Hadoop MapReduce projects designed to solve distributed analytics and data processing problems using scalable parallel computing techniques.

The implementations focus on:
- Efficient data partitioning
- Distributed joins
- Cache optimization
- Aggregation pipelines
- Large dataset processing

All projects were implemented following Hadoop best practices with support for scalable datasets and fault-tolerant processing.

---

# Technologies Used

- Java
- Hadoop MapReduce
- HDFS
- Distributed Cache
- Custom Partitioners
- MultipleInputs
- Writable / WritableComparable

---

# Implemented Concepts

## 1. Distributed Cache
Implemented distributed cache techniques to load lookup/reference datasets into memory during mapper setup for efficient in-mapper joins and enrichment operations.

### Examples:
- Employee Department Enrichment
- Flight Route Enrichment
- Store Region Revenue Lookup
- Movie Genre Rating Analysis

---

## 2. Reduce-Side Joins
Built Reduce-Side Join pipelines using MultipleInputs and tagged mapper outputs to combine large distributed datasets.

### Examples:
- Order Customer Join
- Employee Payroll Join
- Course Enrollment Summary Join

---

## 3. Custom Partitioners
Implemented custom Hadoop partitioners to distribute records across reducers based on specific business rules and partitioning strategies.

### Examples:
- Status-Based Partitioning
- Country-Based Partitioning
- Product Category Partitioning
- Department-Based Partitioning
- Semester-Based Partitioning

---

# Key Features

- Scalable distributed processing
- Parallel analytics pipelines
- Custom Mapper and Reducer implementations
- Large dataset support (1GB+)
- Error handling and validation
- Optimized reducer distribution
- Efficient partitioning strategies

---

# Repository Structure

```bash
├── Task 1 Distributed task/
├── Task 17 Status-Based Partitioning/
├── README.md
```

---

# Example Functionalities

The implemented projects support:
- Aggregation and analytics
- Distributed joins
- Revenue calculations
- Status-based analytics
- Partitioned processing
- Data enrichment
- Parallel computation

---

# Learning Outcomes

Through these projects, the following concepts were practiced:
- Distributed systems
- Big Data processing
- Hadoop ecosystem fundamentals
- Parallel computing
- Data partitioning strategies
- Scalable analytics pipelines

---

# Author

## Loay Magdy

AI & Data Science Enthusiast

GitHub:
https://github.com/loaymagdy38
