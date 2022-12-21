#version 400 core

layout (location = 0) in vec3 aVertexPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aUV;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;
out vec2 vUV;

void main() {

    vColor = aColor;
    vUV = aUV;

    gl_Position = uProjection * uView * vec4(aVertexPosition, 1.0);

}