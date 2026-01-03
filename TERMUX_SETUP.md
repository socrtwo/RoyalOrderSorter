# Termux Commands for Royal Order Sorter GitHub Repository

## PART 1: One-Time Setup (Run Commands INDIVIDUALLY)

These commands require interaction or confirmation. Run each one separately and wait for it to complete:

```bash
pkg update && pkg upgrade -y
```

```bash
pkg install git gh unzip -y
```

```bash
git config --global user.name "Paul Pruitt"
```

```bash
git config --global user.email "YOUR_EMAIL@example.com"
```

```bash
termux-setup-storage
```
*(Grant permission when Android prompts)*

```bash
gh auth login
```
*(Follow the interactive prompts - choose GitHub.com, HTTPS, Yes to authenticate, Login with browser)*

---

## PART 2: Create Repository (BATCH - Copy/Paste All At Once)

After completing Part 1, copy and paste this entire block:

```bash
cd ~/storage/shared/Download && \
unzip -o RoyalOrderSorter.zip && \
cd RoyalOrderSorter && \
git init && \
git add . && \
git commit -m "Initial commit: Royal Order Sorter

- Android app with AI-powered adjective/adverb sorting
- Web service version (web/RoyalOrderSorter.html)
- Uses Transformers.js with Xenova/all-MiniLM-L6-v2 model
- Supports export to TXT, DOCX, and PDF
- Concept by Paul Pruitt"
```

---

## PART 3: Push to GitHub (Run INDIVIDUALLY)

Create the GitHub repository:
```bash
gh repo create RoyalOrderSorter --public --description "AI-powered tool to reorder adjectives and adverbs according to English Royal Order grammar rules" --source=. --remote=origin
```

Push your code:
```bash
git push -u origin main
```

---

## PART 4: Verify Success (BATCH - Optional)

```bash
echo "=== Repository Status ===" && \
git status && \
echo "" && \
echo "=== Remote URL ===" && \
git remote -v && \
echo "" && \
echo "=== Recent Commits ===" && \
git log --oneline -3
```

---

## Quick Reference: Open Your Repo in Browser

```bash
gh repo view --web
```

---

## Troubleshooting

**If the ZIP is in a different location:**
```bash
cd ~/storage/shared/YOUR_FOLDER_NAME && unzip -o RoyalOrderSorter.zip
```

**If git asks for default branch name:**
```bash
git config --global init.defaultBranch main
```

**If you need to start over:**
```bash
cd ~/storage/shared/Download && rm -rf RoyalOrderSorter && unzip -o RoyalOrderSorter.zip
```

---

## Summary

| Part | Type | Why |
|------|------|-----|
| Part 1 | Individual | Requires user interaction/confirmation |
| Part 2 | Batch | Automated file operations |
| Part 3 | Individual | GitHub API calls that may prompt |
| Part 4 | Batch | Read-only verification |
