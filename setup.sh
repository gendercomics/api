#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

log() { echo "[$(date '+%H:%M:%S')] $*"; }

log "=== gendercomics API Setup ==="

log "Ensuring external networks exist..."
docker network create proxy 2>/dev/null && log "  proxy: created" || log "  proxy: already exists"
echo ""

log "Starting API..."
docker compose -f "$SCRIPT_DIR/docker-compose.yml" up -d

echo ""
log "=== Done ==="
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
