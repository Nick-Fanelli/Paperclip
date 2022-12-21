#version 400 core

in vec4 vColor;
in vec2 vUV;

out vec4 outColor;

void main() {
    float distance = 1.0 - length(vUV);
    distance = step(0.0, distance);

    outColor = vColor;
    outColor.a *= distance;
}