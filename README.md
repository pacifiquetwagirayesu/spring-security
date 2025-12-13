<svg viewBox="0 0 1200 1400" xmlns="http://www.w3.org/2000/svg">
  <!-- Background -->
  <rect width="1200" height="1400" fill="#0d1117"/>

  <!-- Title -->
  <text x="600" y="40" font-family="Arial, sans-serif" font-size="28" font-weight="bold" fill="#58a6ff" text-anchor="middle">
    Backend spring security
  </text>
  <text x="600" y="70" font-family="Arial, sans-serif" font-size="16" fill="#8b949e" text-anchor="middle">
    Enterprise-Grade Security Backend with Stateful JWT Authentication
  </text>

  <!-- Live Demo Badge -->
  <rect x="350" y="85" width="250" height="30" rx="15" fill="#238636"/>
  <text x="475" y="105" font-family="Arial, sans-serif" font-size="14" fill="#ffffff" text-anchor="middle" font-weight="bold">
    🌐 LIVE: http://51.44.171.166/
  </text>

  <!-- Docker Hub Badge -->
  <rect x="620" y="85" width="230" height="30" rx="15" fill="#0db7ed"/>
  <text x="735" y="105" font-family="Arial, sans-serif" font-size="14" fill="#ffffff" text-anchor="middle" font-weight="bold">
    🐳 Docker Hub Image
  </text>

  <!-- Main Container -->
  <rect x="50" y="140" width="1100" height="640" rx="10" fill="#161b22" stroke="#30363d" stroke-width="2"/>

  <!-- Tech Stack Section -->
  <rect x="70" y="160" width="340" height="280" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="240" y="190" font-family="Arial, sans-serif" font-size="18" font-weight="bold" fill="#58a6ff" text-anchor="middle">
    🛠️ Tech Stack
  </text>

  <!-- Tech Stack Items -->
  <g transform="translate(90, 210)">
    <rect width="300" height="35" rx="4" fill="#21262d"/>
    <text x="10" y="23" font-family="monospace" font-size="13" fill="#7ee787" font-weight="bold">Java 17</text>
    <text x="250" y="23" font-family="Arial, sans-serif" font-size="12" fill="#8b949e" text-anchor="end">Language</text>
  </g>

  <g transform="translate(90, 250)">
    <rect width="300" height="35" rx="4" fill="#21262d"/>
    <text x="10" y="23" font-family="monospace" font-size="13" fill="#7ee787" font-weight="bold">Spring Boot</text>
    <text x="250" y="23" font-family="Arial, sans-serif" font-size="12" fill="#8b949e" text-anchor="end">Framework</text>
  </g>

  <g transform="translate(90, 290)">
    <rect width="300" height="35" rx="4" fill="#21262d"/>
    <text x="10" y="23" font-family="monospace" font-size="13" fill="#7ee787" font-weight="bold">Gradle</text>
    <text x="250" y="23" font-family="Arial, sans-serif" font-size="12" fill="#8b949e" text-anchor="end">Build Tool</text>
  </g>

  <g transform="translate(90, 330)">
    <rect width="300" height="35" rx="4" fill="#21262d"/>
    <text x="10" y="23" font-family="monospace" font-size="13" fill="#7ee787" font-weight="bold">PostgreSQL</text>
    <text x="250" y="23" font-family="Arial, sans-serif" font-size="12" fill="#8b949e" text-anchor="end">Database</text>
  </g>

  <g transform="translate(90, 370)">
    <rect width="300" height="35" rx="4" fill="#21262d"/>
    <text x="10" y="23" font-family="monospace" font-size="13" fill="#7ee787" font-weight="bold">Docker + GitHub Actions</text>
    <text x="250" y="23" font-family="Arial, sans-serif" font-size="12" fill="#8b949e" text-anchor="end">CI/CD</text>
  </g>

  <!-- Security Architecture Section -->
  <rect x="430" y="160" width="700" height="280" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="780" y="190" font-family="Arial, sans-serif" font-size="18" font-weight="bold" fill="#f85149" text-anchor="middle">
    🔐 Security Architecture
  </text>

  <!-- Stateful JWT Box -->
  <rect x="450" y="210" width="320" height="210" rx="6" fill="#1c2128" stroke="#f85149" stroke-width="2"/>
  <text x="610" y="235" font-family="Arial, sans-serif" font-size="15" font-weight="bold" fill="#ffffff" text-anchor="middle">
    Stateful JWT Authentication
  </text>

  <text x="460" y="260" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="460" dy="0">✓ JWT generated on login</tspan>
    <tspan x="460" dy="22">✓ Token stored in database</tspan>
    <tspan x="460" dy="22">✓ Validated against DB per request</tspan>
    <tspan x="460" dy="22">✓ Revocable sessions</tspan>
    <tspan x="460" dy="22">✓ Enterprise audit capability</tspan>
  </text>

  <rect x="460" y="365" width="300" height="45" rx="4" fill="#388bfd20" stroke="#58a6ff" stroke-width="1"/>
  <text x="610" y="385" font-family="Arial, sans-serif" font-size="11" fill="#58a6ff" text-anchor="middle" font-weight="bold">
    Better Control + Security
  </text>
  <text x="610" y="400" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">
    vs Traditional Stateless JWT
  </text>

  <!-- Security Features Box -->
  <rect x="790" y="210" width="320" height="210" rx="6" fill="#1c2128" stroke="#f85149" stroke-width="2"/>
  <text x="950" y="235" font-family="Arial, sans-serif" font-size="15" font-weight="bold" fill="#ffffff" text-anchor="middle">
    Advanced Security Features
  </text>

  <text x="800" y="260" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="800" dy="0">🛡️ Custom Authentication Filter</tspan>
    <tspan x="810" dy="20" font-size="10">• JWT extraction &amp; validation</tspan>
    <tspan x="810" dy="16" font-size="10">• User session loading</tspan>

    <tspan x="800" dy="28">🔒 Method-Level Authorization</tspan>
    <tspan x="810" dy="20" font-size="10">• @PreAuthorize / @PostAuthorize</tspan>
    <tspan x="810" dy="16" font-size="10">• Fine-grained access control</tspan>
    
    <tspan x="800" dy="28">⚙️ Custom PermissionEvaluator</tspan>
    <tspan x="810" dy="20" font-size="10">• Complex permission logic</tspan>
    <tspan x="810" dy="16" font-size="10">• Resource-level authorization</tspan>
  </text>

  <!-- Deployment Flow Section -->
  <rect x="70" y="460" width="1060" height="300" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="600" y="490" font-family="Arial, sans-serif" font-size="18" font-weight="bold" fill="#a371f7" text-anchor="middle">
    🚀 CI/CD Pipeline Flow
  </text>

  <!-- GitHub -->
  <rect x="110" y="520" width="160" height="80" rx="6" fill="#21262d" stroke="#58a6ff" stroke-width="2"/>
  <text x="190" y="550" font-family="Arial, sans-serif" font-size="14" fill="#58a6ff" text-anchor="middle" font-weight="bold">GitHub</text>
  <text x="190" y="570" font-family="Arial, sans-serif" font-size="11" fill="#8b949e" text-anchor="middle">Source Code</text>
  <text x="190" y="587" font-family="monospace" font-size="10" fill="#7ee787" text-anchor="middle">git push</text>

  <!-- Arrow 1 -->
  <path d="M 270 560 L 320 560" stroke="#8b949e" stroke-width="2" fill="none" marker-end="url(#arrowhead)"/>
  <text x="295" y="550" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">trigger</text>

  <!-- GitHub Actions -->
  <rect x="320" y="520" width="160" height="80" rx="6" fill="#21262d" stroke="#f85149" stroke-width="2"/>
  <text x="400" y="550" font-family="Arial, sans-serif" font-size="14" fill="#f85149" text-anchor="middle" font-weight="bold">GitHub Actions</text>
  <text x="400" y="570" font-family="Arial, sans-serif" font-size="11" fill="#8b949e" text-anchor="middle">Build &amp; Test</text>
  <text x="400" y="587" font-family="monospace" font-size="10" fill="#7ee787" text-anchor="middle">gradle build</text>

  <!-- Arrow 2 -->
  <path d="M 480 560 L 530 560" stroke="#8b949e" stroke-width="2" fill="none" marker-end="url(#arrowhead)"/>
  <text x="505" y="550" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">docker build</text>

  <!-- Docker Build -->
  <rect x="530" y="520" width="160" height="80" rx="6" fill="#21262d" stroke="#1e90ff" stroke-width="2"/>
  <text x="610" y="550" font-family="Arial, sans-serif" font-size="14" fill="#1e90ff" text-anchor="middle" font-weight="bold">Docker Image</text>
  <text x="610" y="570" font-family="Arial, sans-serif" font-size="11" fill="#8b949e" text-anchor="middle">Containerize</text>
  <text x="610" y="587" font-family="monospace" font-size="10" fill="#7ee787" text-anchor="middle">Multi-stage build</text>

  <!-- Arrow 3 -->
  <path d="M 690 560 L 740 560" stroke="#8b949e" stroke-width="2" fill="none" marker-end="url(#arrowhead)"/>
  <text x="715" y="550" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">push</text>

  <!-- Docker Hub -->
  <rect x="740" y="520" width="160" height="80" rx="6" fill="#21262d" stroke="#0db7ed" stroke-width="2"/>
  <text x="820" y="540" font-family="Arial, sans-serif" font-size="14" fill="#0db7ed" text-anchor="middle" font-weight="bold">Docker Hub</text>
  <text x="820" y="558" font-family="monospace" font-size="10" fill="#ffa657" text-anchor="middle">paccy/backend-</text>
  <text x="820" y="572" font-family="monospace" font-size="10" fill="#ffa657" text-anchor="middle">spring-security</text>
  <text x="820" y="587" font-family="Arial, sans-serif" font-size="9" fill="#58a6ff" text-anchor="middle">hub.docker.com</text>
  <text x="820" y="598" font-family="Arial, sans-serif" font-size="8" fill="#8b949e" text-anchor="middle">Public Registry</text>

  <!-- Arrow 4 -->
  <path d="M 900 560 L 950 560" stroke="#8b949e" stroke-width="2" fill="none" marker-end="url(#arrowhead)"/>
  <text x="925" y="550" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">deploy</text>

  <!-- Production -->
  <rect x="950" y="520" width="160" height="80" rx="6" fill="#21262d" stroke="#238636" stroke-width="2"/>
  <text x="1030" y="545" font-family="Arial, sans-serif" font-size="14" fill="#238636" text-anchor="middle" font-weight="bold">Production</text>
  <text x="1030" y="565" font-family="Arial, sans-serif" font-size="11" fill="#8b949e" text-anchor="middle">EC2 Instance</text>
  <text x="1030" y="582" font-family="monospace" font-size="10" fill="#7ee787" text-anchor="middle">51.44.171.166</text>
  <text x="1030" y="595" font-family="Arial, sans-serif" font-size="9" fill="#8b949e" text-anchor="middle">docker-compose up</text>

  <!-- Runtime Architecture -->
  <rect x="110" y="650" width="1000" height="90" rx="6" fill="#1c2128" stroke="#a371f7" stroke-width="1" stroke-dasharray="4"/>
  <text x="610" y="675" font-family="Arial, sans-serif" font-size="14" fill="#a371f7" text-anchor="middle" font-weight="bold">
    🗄️ Runtime Architecture
  </text>

  <g transform="translate(140, 690)">
    <rect width="200" height="35" rx="4" fill="#238636" opacity="0.2"/>
    <text x="100" y="23" font-family="Arial, sans-serif" font-size="12" fill="#7ee787" text-anchor="middle">Spring Boot App</text>
  </g>

  <path d="M 340 707 L 390 707" stroke="#a371f7" stroke-width="2" fill="none"/>
  <text x="365" y="700" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">JDBC</text>

  <g transform="translate(390, 690)">
    <rect width="200" height="35" rx="4" fill="#1e90ff" opacity="0.2"/>
    <text x="100" y="23" font-family="Arial, sans-serif" font-size="12" fill="#58a6ff" text-anchor="middle">PostgreSQL</text>
  </g>

  <path d="M 590 707 L 640 707" stroke="#a371f7" stroke-width="2" fill="none"/>
  <text x="615" y="700" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">stores</text>

  <g transform="translate(640, 690)">
    <rect width="200" height="35" rx="4" fill="#f85149" opacity="0.2"/>
    <text x="100" y="23" font-family="Arial, sans-serif" font-size="12" fill="#f85149" text-anchor="middle">JWT Tokens + Users</text>
  </g>

  <path d="M 840 707 L 890 707" stroke="#a371f7" stroke-width="2" fill="none"/>
  <text x="865" y="700" font-family="Arial, sans-serif" font-size="10" fill="#8b949e" text-anchor="middle">validates</text>

  <g transform="translate(890, 690)">
    <rect width="200" height="35" rx="4" fill="#ffa657" opacity="0.2"/>
    <text x="100" y="23" font-family="Arial, sans-serif" font-size="12" fill="#ffa657" text-anchor="middle">Every Request</text>
  </g>

  <!-- Additional Details Section -->
  <rect x="50" y="810" width="1100" height="560" rx="10" fill="#161b22" stroke="#30363d" stroke-width="2"/>

  <!-- Project Overview -->
  <rect x="70" y="830" width="1060" height="140" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="600" y="860" font-family="Arial, sans-serif" font-size="18" font-weight="bold" fill="#58a6ff" text-anchor="middle">
    📋 Project Overview
  </text>

  <text x="100" y="895" font-family="Arial, sans-serif" font-size="13" fill="#c9d1d9">
    <tspan x="100" dy="0">Secure backend application demonstrating enterprise-grade security implementation with</tspan>
    <tspan x="100" dy="22">stateful JWT authentication, custom permission logic, method-level authorization, and</tspan>
    <tspan x="100" dy="22">automated CI/CD deployment via Docker.</tspan>
  </text>

  <!-- Security Details -->
  <rect x="70" y="990" width="510" height="360" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="325" y="1020" font-family="Arial, sans-serif" font-size="16" font-weight="bold" fill="#f85149" text-anchor="middle">
    🔐 Security Implementation
  </text>

  <text x="90" y="1050" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    1. Stateful JWT Authentication
  </text>
  <text x="100" y="1075" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="100" dy="0">• JWT tokens generated on login</tspan>
    <tspan x="100" dy="18">• Stored in database (not just client-side)</tspan>
    <tspan x="100" dy="18">• Validated against DB per request</tspan>
    <tspan x="100" dy="18">• Tokens can be revoked (logout, security)</tspan>
    <tspan x="100" dy="18">• Full audit trail of active sessions</tspan>
  </text>

  <text x="90" y="1190" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    2. Custom Authentication Filter
  </text>
  <text x="100" y="1215" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="100" dy="0">• Extracts JWT from request headers</tspan>
    <tspan x="100" dy="18">• Validates token and loads user session</tspan>
    <tspan x="100" dy="18">• Sets Authentication in security context</tspan>
  </text>

  <text x="90" y="1290" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    3. Method-Level Authorization
  </text>
  <text x="100" y="1315" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="100" dy="0">• @PreAuthorize - Check before execution</tspan>
    <tspan x="100" dy="18">• @PostAuthorize - Validate after execution</tspan>
    <tspan x="100" dy="18">• @PostFilter - Filter returned collections</tspan>
  </text>

  <!-- Benefits Section -->
  <rect x="600" y="990" width="530" height="360" rx="8" fill="#0d1117" stroke="#30363d" stroke-width="1"/>
  <text x="865" y="1020" font-family="Arial, sans-serif" font-size="16" font-weight="bold" fill="#238636" text-anchor="middle">
    ✨ Key Benefits
  </text>

  <text x="620" y="1055" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    Security Advantages
  </text>
  <text x="630" y="1080" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="630" dy="0">✓ Complete session control</tspan>
    <tspan x="630" dy="18">✓ Instant token revocation capability</tspan>
    <tspan x="630" dy="18">✓ Protection against compromised tokens</tspan>
    <tspan x="630" dy="18">✓ Enterprise-grade audit trails</tspan>
  </text>

  <text x="620" y="1180" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    Development Advantages
  </text>
  <text x="630" y="1205" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="630" dy="0">✓ Automated CI/CD pipeline</tspan>
    <tspan x="630" dy="18">✓ Multi-stage Docker optimization</tspan>
    <tspan x="630" dy="18">✓ Production-ready deployment</tspan>
    <tspan x="630" dy="18">✓ Integrated API documentation</tspan>
  </text>

  <text x="620" y="1290" font-family="Arial, sans-serif" font-size="13" fill="#58a6ff" font-weight="bold">
    vs Traditional Stateless JWT
  </text>
  <text x="630" y="1315" font-family="Arial, sans-serif" font-size="12" fill="#8b949e">
    <tspan x="630" dy="0">✓ Better security: Revocable sessions</tspan>
    <tspan x="630" dy="18">✓ Better control: Active session management</tspan>
    <tspan x="630" dy="18">✓ Better audit: Complete activity tracking</tspan>
  </text>

  <!-- Arrow marker definition -->
  <defs>
    <marker id="arrowhead" markerWidth="10" markerHeight="10" refX="9" refY="3" orient="auto">
      <polygon points="0 0, 10 3, 0 6" fill="#8b949e"/>
    </marker>
  </defs>

  <!-- Footer -->
  <text x="600" y="1385" font-family="Arial, sans-serif" font-size="11" fill="#58a6ff" text-anchor="middle">
    📚 Full API Documentation available via Swagger UI at the live deployment
  </text>
</svg>