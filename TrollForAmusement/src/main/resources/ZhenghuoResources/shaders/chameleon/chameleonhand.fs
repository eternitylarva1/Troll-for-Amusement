#ifdef GL_ES
#define LOWP lowp
precision highp float; // 修改为 highp 精度
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D u_texture;
uniform vec2 u_resolution;

void main() {
    vec2 texelSize = 1.0 / u_resolution;
    vec2 uv = v_texCoord;

    // Gaussian blur kernel
    mat3 blurKernel = mat3(
        1.0, 2.0, 1.0,
        2.0, 4.0, 2.0,
        1.0, 2.0, 1.0
    ) / 16.0;

    vec3 blurredColor = vec3(0.0);

    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            vec2 offset = vec2(i, j) * texelSize;
            vec2 sampleUV = clamp(uv + offset, 0.0, 1.0); // 确保采样坐标在 [0, 1] 范围内
            vec3 sample = texture2D(u_texture, sampleUV).rgb;
            blurredColor += sample * blurKernel[i + 1][j + 1];
        }
    }

    // Sobel filter kernels
    mat3 sobelX = mat3(
        -1.0, 0.0, 1.0,
        -2.0, 0.0, 2.0,
        -1.0, 0.0, 1.0
    );

    mat3 sobelY = mat3(
        -1.0, -2.0, -1.0,
         0.0,  0.0,  0.0,
         1.0,  2.0,  1.0
    );

    float sobelThreshold = 0.5; // 增加阈值

    vec3 sobelXResult = vec3(0.0);
    vec3 sobelYResult = vec3(0.0);

    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            vec2 offset = vec2(i, j) * texelSize;
            vec2 sampleUV = clamp(uv + offset, 0.0, 1.0); // 确保采样坐标在 [0, 1] 范围内
            vec3 sample = texture2D(u_texture, sampleUV).rgb;
            sobelXResult += sample * sobelX[i + 1][j + 1];
            sobelYResult += sample * sobelY[i + 1][j + 1];
        }
    }

    float edgeStrength = length(sobelXResult) + length(sobelYResult);

    float smoothEdge = smoothstep(sobelThreshold - 0.1, sobelThreshold + 0.1, edgeStrength);
    vec4 finalColor = mix(vec4(0.0, 0.0, 0.0, 1.0), vec4(1.0, 1.0, 1.0, 1.0), smoothEdge);

    gl_FragColor = finalColor;
}