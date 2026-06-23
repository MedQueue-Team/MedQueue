---
name: MediQueue Operations
colors:
  surface: '#f9f9fe'
  surface-dim: '#d9dade'
  surface-bright: '#f9f9fe'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f3f3f8'
  surface-container: '#ededf2'
  surface-container-high: '#e8e8ec'
  surface-container-highest: '#e2e2e7'
  on-surface: '#1a1c1f'
  on-surface-variant: '#42474f'
  inverse-surface: '#2f3034'
  inverse-on-surface: '#f0f0f5'
  outline: '#727780'
  outline-variant: '#c2c6d0'
  surface-tint: '#336093'
  primary: '#00284b'
  on-primary: '#ffffff'
  primary-container: '#003e6f'
  on-primary-container: '#7faae1'
  inverse-primary: '#a1c9ff'
  secondary: '#556158'
  on-secondary: '#ffffff'
  secondary-container: '#d9e6da'
  on-secondary-container: '#5b675e'
  tertiary: '#431c00'
  on-tertiary: '#ffffff'
  tertiary-container: '#642d01'
  on-tertiary-container: '#e59461'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d2e4ff'
  primary-fixed-dim: '#a1c9ff'
  on-primary-fixed: '#001c38'
  on-primary-fixed-variant: '#14487a'
  secondary-fixed: '#d9e6da'
  secondary-fixed-dim: '#bdcabf'
  on-secondary-fixed: '#131e17'
  on-secondary-fixed-variant: '#3e4a41'
  tertiary-fixed: '#ffdbc8'
  tertiary-fixed-dim: '#ffb68a'
  on-tertiary-fixed: '#321300'
  on-tertiary-fixed-variant: '#71370a'
  background: '#f9f9fe'
  on-background: '#1a1c1f'
  surface-variant: '#e2e2e7'
typography:
  display-lg:
    fontFamily: Manrope
    fontSize: 48px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Manrope
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.25'
  headline-lg-mobile:
    fontFamily: Manrope
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.3'
  headline-md:
    fontFamily: Manrope
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.3'
  headline-sm:
    fontFamily: Manrope
    fontSize: 20px
    fontWeight: '600'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.5'
  body-sm:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: '1.5'
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1.2'
    letterSpacing: 0.05em
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.2'
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '500'
    lineHeight: '1.2'
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 8px
  container-max: 1440px
  gutter: 24px
  margin-desktop: 40px
  margin-tablet: 24px
  margin-mobile: 16px
---

## Brand & Style
The design system is engineered for high-stakes healthcare environments where clarity saves lives and efficiency reduces burnout. The "Clinical-Tech" aesthetic merges the sterile precision of medical instruments with the intuitive fluidity of modern enterprise software.

The visual language prioritizes **Trust, Efficiency, and Accessibility**. It employs a minimalist framework that utilizes generous whitespace to reduce cognitive load for practitioners and administrators. The result is a UI that feels authoritative yet approachable, ensuring that critical data is always the primary focus.

## Colors
This design system uses a high-contrast palette to ensure legibility across various lighting conditions found in medical facilities.

- **Primary (Deep Navy):** Represents stability and institutional trust. Used for primary actions and brand presence.
- **Secondary (Clinical Mint/Olive):** A grounded, organic tone used for secondary actions and subtle differentiation in data visualization.
- **Background (Soft Blue-White):** A cool-toned neutral that reduces screen glare compared to pure white, providing a "clean-room" feel.
- **Surface (Pure White):** Reserved for elevated containers, cards, and input fields to draw the eye toward interactive elements.

## Typography
The typography system balances the modern, geometric friendliness of **Manrope** for headers with the systematic utility of **Inter** for data-dense body content.

Headlines should be used sparingly to define clear sections. Body text uses a standard 1.5x to 1.6x line height to ensure maximum readability during quick scans. For technical labels and status indicators, uppercase styling with slight letter spacing is encouraged to distinguish them from narrative text.

## Layout & Spacing
The layout follows a 12-column fluid grid system on desktop, transitioning to a 4-column system on mobile. 

A strict **8px base unit** governs all spacing decisions. 
- Use **40px (5 units)** for major section vertical padding on desktop.
- Use **24px (3 units)** for internal card padding and gutters.
- Use **16px (2 units)** for smaller component groupings.

All layouts should prioritize a "Top-Left" focus, following natural reading patterns for data entry and monitoring. Elements should be grouped logically using whitespace rather than dividers wherever possible to maintain the "Clinical-Tech" minimalism.

## Elevation & Depth
Elevation in the design system is subtle, mimicking the way light hits flat medical surfaces.

- **Level 0 (Background):** Soft Blue-White (#F8F9FF). All structural layouts begin here.
- **Level 1 (Surface):** Pure White (#FFFFFF) surfaces used for cards and main content blocks. These use a very soft ambient shadow: `0px 4px 12px rgba(0, 0, 0, 0.05)`.
- **Level 2 (Interactive/Overlay):** Modals and dropdowns. These use a more pronounced but still diffused shadow: `0px 8px 24px rgba(0, 0, 0, 0.08)`.

Avoid heavy gradients or dark shadows. Depth should feel "airy" and intentional.

## Shapes
The shape language is precise and controlled. 

- **Buttons & Inputs:** Use a 0.5rem (8px) radius. This provides a professional "softened-tech" look that is approachable but remains corporate.
- **Containers & Cards:** Use a 0.75rem (12px) radius to distinguish structural elements from interactive ones.
- **Status Badges:** Use fully rounded (pill-shaped) geometry to clearly separate status indicators from buttons or data fields.

## Components

### Buttons
- **Primary:** Deep Navy background with white text. 8px corner radius.
- **Secondary:** Transparent background with 2px Deep Navy border.
- **Tertiary:** Pure Mint/Olive text with no background or border.

### Badges & Tags
- Always pill-shaped.
- Use low-opacity tints of the status colors (e.g., Success, Warning) with high-contrast dark text for accessibility.

### Input Fields
- White background with a 1px border (#D1D5DB).
- 8px corner radius.
- Focus state: 2px Deep Navy border with a 3px soft blue outer glow.

### Cards
- Pure white background.
- 12px corner radius.
- Level 1 ambient shadow.
- 24px internal padding.

### Icons
- Use 2px line-style icons exclusively.
- Icons should be monochromatic (Secondary color) unless used as a critical status warning (Red).

### Lists
- Use subtle 1px horizontal dividers (#E5E7EB).
- High vertical padding (16px) between items to ensure touch-targets are accessible for tablets in clinical settings.