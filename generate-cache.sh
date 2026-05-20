#!/bin/bash
set -e

CACHE_DIR="$HOME/.m2/repository/cache-data"
TARGET_SIZE=$((30 * 1024 * 1024 * 1024))  # 30 GB
CURRENT_SIZE=0
FILE_NUM=0
CHUNK_SIZE=$((100 * 1024 * 1024))  # 100 MB

echo "=================================="
echo "Starting 30GB cache generation..."
echo "Target directory: $CACHE_DIR"
echo "=================================="

mkdir -p "$CACHE_DIR"

while [ $CURRENT_SIZE -lt $TARGET_SIZE ]; do
    FILE_NAME="cache-${FILE_NUM}.bin"

    # Generate 100MB file
    dd if=/dev/urandom of="$CACHE_DIR/$FILE_NAME" bs=1M count=100 2>/dev/null

    CURRENT_SIZE=$((CURRENT_SIZE + CHUNK_SIZE))
    FILE_NUM=$((FILE_NUM + 1))

    # Progress every 10 files
    if [ $((FILE_NUM % 10)) -eq 0 ]; then
        SIZE_GB=$((CURRENT_SIZE / 1024 / 1024 / 1024))
        echo "Generated $FILE_NUM files, total: ${SIZE_GB}GB"
    fi
done

echo ""
echo "=================================="
echo "30GB cache generation complete!"
echo "=================================="
du -sh "$CACHE_DIR"
ls -1 "$CACHE_DIR" | wc -l | xargs echo "Total files:"
