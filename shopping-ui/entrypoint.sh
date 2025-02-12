#!/bin/sh

# Set default BASE_URL to '/'
BASE_URL="${BASE_URL:-/}"

# Clean BASE_URL by removing leading/trailing slashes
CLEANED_BASE_URL=$(echo "${BASE_URL}" | sed 's|^/||; s|/$||')

# Determine the replacement base URL value
if [ -n "${CLEANED_BASE_URL}" ]; then
  REPLACEMENT="/${CLEANED_BASE_URL}/"
else
  REPLACEMENT="/"
fi

# Move files to subdirectory if BASE_URL is not empty
TARGET_DIR="/usr/share/nginx/html"
if [ -n "${CLEANED_BASE_URL}" ]; then
  TARGET_DIR="/usr/share/nginx/html/${CLEANED_BASE_URL}"
  mkdir -p "${TARGET_DIR}"
  mv /usr/share/nginx/html/* "${TARGET_DIR}/"
  mv /usr/share/nginx/html/.* "${TARGET_DIR}/" 2>/dev/null || true
fi

# Replace placeholder in all relevant files
find "${TARGET_DIR}" -type f \( -name "*.html" -o -name "*.js" -o -name "*.css" -o -name "*.json" \) -exec sed -i "s|__BASE_URL__|${REPLACEMENT}|g" {} \;

# Generate Nginx configuration based on BASE_URL
if [ -n "${CLEANED_BASE_URL}" ]; then
  cat <<EOF > /etc/nginx/conf.d/default.conf
server {
    listen       80;
    server_name  localhost;

    location /${CLEANED_BASE_URL}/ {
        alias ${TARGET_DIR}/;
        try_files \$uri \$uri/ /${CLEANED_BASE_URL}/index.html;
    }

    location = / {
        return 301 /${CLEANED_BASE_URL}/;
    }

    location /${CLEANED_BASE_URL} {
        return 301 /${CLEANED_BASE_URL}/;
    }
}
EOF
else
  cat <<EOF > /etc/nginx/conf.d/default.conf
server {
    listen       80;
    server_name  localhost;

    location / {
        root   ${TARGET_DIR};
        try_files \$uri \$uri/ /index.html;
    }
}
EOF
fi

exec "$@"