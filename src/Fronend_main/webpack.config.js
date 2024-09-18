const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WarningsToErrorsPlugin = require('warnings-to-errors-webpack-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

module.exports = (env, argv) => ({
  entry: './src/index.js', // Ruta de entrada para React
  output: {
    path: path.resolve(__dirname, './target/classes/static'), // El bundle se genera en la carpeta de recursos estáticos
    filename: 'js/bundle.js', // Nombre del archivo de salida para el bundle
    publicPath: '/' // Ruta pública para que el servidor sirva el archivo estático
  },
  devtool: argv.mode === 'production' ? false : 'eval-source-map', // Source maps en modo desarrollo
  performance: {
    maxEntrypointSize: 488000,
    maxAssetSize: 488000
  },
  optimization: {
    minimize: true, // Minimización para producción
    minimizer: [
      new TerserPlugin(),
      new CssMinimizerPlugin()
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "css/bundle.css" // Nombre del archivo CSS generado
    }),
    new WarningsToErrorsPlugin() // Convierte advertencias en errores
  ],
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/, // Procesa archivos JSX y JS de React
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react'] // Presets para procesar ES6+ y JSX
          }
        }
      },
      {
        test: /\.tsx?$/, // Procesa archivos TypeScript (si es necesario)
        use: ['ts-loader']
      },
      {
        test: /\.scss$/, // Procesa archivos SCSS (Sass)
        include: path.resolve(__dirname, './src/scss'),
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1,
              sourceMap: true
            }
          },
          {
            loader: 'postcss-loader', // Usa PostCSS para procesar el CSS
            options: {
              postcssOptions: {
                plugins: [
                  require('autoprefixer'), // Añade prefijos para compatibilidad con navegadores
                ]
              },
              sourceMap: true
            }
          },
          {
            loader: 'sass-loader', // Compila SCSS a CSS
            options: { sourceMap: true }
          }
        ]
      },
      {
        test: /\.css$/, // Procesa archivos CSS
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader', // Usa MiniCssExtractPlugin en producción
          'css-loader'
        ]
      }
    ]
  },
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'], // Extensiones soportadas para imports
    plugins: [new TsconfigPathsPlugin({})], // Soporte para paths de TypeScript
  },
  devServer: {
    port: 8082, // Puerto para el frontend
    compress: true, // Compresión en el servidor de desarrollo
    historyApiFallback: true, // Maneja rutas del frontend correctamente en React
    static: {
      directory: path.resolve(__dirname, 'public'), // Sirve contenido estático desde esta carpeta
    },
    watchFiles: [
      'src/**/*.html', // Vigila cambios en archivos HTML
      'src/**/*.ts', // Vigila cambios en archivos TypeScript
      'src/**/*.scss', // Vigila cambios en archivos SCSS
      'src/**/*.css' // Vigila cambios en archivos CSS
    ],
    proxy: [
      {
        context: ['/api'], // Proxy para las rutas API
        target: 'http://localhost:8080', // URL donde se está ejecutando el backend de Spring Boot
        secure: false, // Desactiva la verificación de SSL (si no es necesaria)
        changeOrigin: true // Cambia el origen de las solicitudes para evitar problemas de CORS
      }
    ]
  }
});


