# README: PGMD Data Curation and Validation Scripts

## 1. Overview
This repository contains custom Python scripts developed for the **Pig Gut Microbiome Database (PGMD)**. These tools automate the cleaning of 16S rRNA sequencing data, standardized integration of nutritional profiles, and cross-table metadata consistency validation.

## 2. Project Structure
The following directory tree outlines the core code files in the data curation pipeline:

```text
PGMDB/
├── __init__.py              # Cross-table validation module
├── Microbe.py               # Taxonomy and abundance processing module
├── project.py               # Metadata standardization module
├── nutrient_composition.py  # Dietary data integration module
├── util.py                  # General utility and cleaning library
├── find_repetition.py       # Redundancy detection tool
├── Literature.py            # Literature mapping tool
├── delete_0.py              # Zero-abundance cleanup script
└── window.py                # Graphical User Interface (GUI)
```

## 3. Core Functionalities

### Detailed File Roles

#### # Microbe.py
The primary engine for microbial data processing. It handles taxonomy resolution (from Phylum to Species), re-calculates relative abundances, and extracts standardized LDA scores from LEfSe analysis results.

#### # project.py
Responsible for cleaning and merging project-level metadata. It standardizes experimental descriptions and phenotype information (e.g., merging birth weight and weaning data) into a unified, machine-readable format.

#### # nutrient_composition.py
Manages the integration of dietary data. It automatically fills missing labels in nutritional tables and maps 12 core dietary components (such as Crude Protein, Calcium, and Starch) to their respective BioProject IDs.

#### # util.py
A foundational utility library providing essential cleaning functions used across other modules, such as removing non-standard taxonomic prefixes (e.g., `k__`, `p__`), stripping square brackets from taxon names, and deleting rows with zero total abundance.

#### # __init__.py
Acts as a data integrity validator. It performs set-based comparisons of BioProject IDs across sample, project, and microbial tables to identify any discrepancies or missing records during the data integration phase.

#### # find_repetition.py
A specialized validation tool designed to ensure data uniqueness by detecting and reporting redundant sample "Run" records within the dataset, preventing statistical bias from duplicate entries.

#### # Literature.py
Transforms manually collected literature metadata into structured source-target formats, enabling the generation of network diagrams for research theme analysis.

#### # delete_0.py
A dedicated cleanup script for the rapid removal of rows where microbial abundance values are zero, ensuring the final processed dataset remains concise and focused on present taxa.

#### # window.py
Provides a user-friendly Graphical User Interface (GUI) built with Tkinter. This allows researchers to perform complex data transformations and name modifications through interactive buttons without manually editing script parameters.



## 4. Usage Flow

### Option 1: Graphical User Interface (GUI)
1.  **Launch**: Run `window.py` to launch the interactive interface.
2.  **Select**: Click **"Browse"** to select your raw data file (CSV or Excel).
3.  **Execute**: Select the desired processing function (e.g., **"Process LEfSe"**, **"Modify Filenames"**, or **"Confirm"**) to execute the automated pipeline.

### Option 2: Scripting Mode
1.  **Standardizing Microbial Data**: Update the input file path in `Microbe.py` and call the `resolve_microbe()` function for batch processing.
2.  **Validating Consistency**: Run `__init__.py` to output any BioProject discrepancies between metadata and taxonomic abundance tables.
