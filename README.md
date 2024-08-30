# my-custom-plugin

plugin for demo

## Install

```bash
npm install my-custom-plugin
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`showAlert(...)`](#showalert)
* [`print(...)`](#print)
* [`printTcp(...)`](#printtcp)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### showAlert(...)

```typescript
showAlert(options: { msg: string; }) => Promise<any>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ msg: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### print(...)

```typescript
print(options: { host: string; shareName: string; domain: string; username: string; password: string; dataToPrint: any; }) => Promise<any>
```

| Param         | Type                                                                                                                    |
| ------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ host: string; shareName: string; domain: string; username: string; password: string; dataToPrint: any; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### printTcp(...)

```typescript
printTcp(options: { host: string; dataToPrint: any; }) => Promise<any>
```

| Param         | Type                                             |
| ------------- | ------------------------------------------------ |
| **`options`** | <code>{ host: string; dataToPrint: any; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------

</docgen-api>
