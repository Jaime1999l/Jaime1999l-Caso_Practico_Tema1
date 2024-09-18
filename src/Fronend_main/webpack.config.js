const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WarningsToErrorsPlugin = require('warnings-to-errors-webpack-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

module.exports = (env, argv) => ({
  entry: './src/index.js', // Ruta de entrada para React
  output: {
    path: path.resolve(__dirname, './target/classes/static'),
    filename: 'js/bundle.js',
    publicPath: '/'
  },
  devtool: argv.mode === 'production' ? false : 'eval-source-map',
  performance: {
    maxEntrypointSize: 488000,
    maxAssetSize: 488000
  },
  optimization: {
    minimize: true,
    minimizer: [
      new TerserPlugin(),
      new CssMinimizerPlugin()
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "css/bundle.css"
    }),
    new WarningsToErrorsPlugin()
  ],
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/, // Procesa archivos JSX de React
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react']
          }
        }
      },
      {
        test: /\.tsx?$/, // Si tienes archivos TypeScript (opcional)
        use: ['ts-loader']
      },
      {
        test: /\.scss$/, // Procesa archivos SCSS
        include: path.resolve(__dirname, './src/scss'), // Asegúrate de que los SCSS estén en esta carpeta
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
            loader: 'postcss-loader',
            options: {
              postcssOptions: {
                plugins: [
                  require('autoprefixer'),
                ]
              },
              sourceMap: true
            }
          },
          {
            loader: 'sass-loader',
            options: { sourceMap: true }
          }
        ]
      },
      {
        test: /\.css$/, // Procesa archivos CSS
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
          'css-loader'
        ]
      }
    ]
  },
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'], // Asegúrate de incluir .jsx si usas React
    plugins: [new TsconfigPathsPlugin({})],
  },
  devServer: {
    port: 8082, // Puerto para el frontend
    compress: true,
    historyApiFallback: true, // Para manejar correctamente rutas en React
    watchFiles: [
      'src/**/*.html', // Observa archivos HTML y SCSS dentro de src
      'src/**/*.ts',
      'src/**/*.scss',
      'src/**/*.css'
    ],
    proxy: [
      {
        context: ['/api'], // El contexto de la API que quieres redirigir
        target: 'http://localhost:8083', // Cambia esto si tu backend está corriendo en el puerto 8083
        secure: false,
        changeOrigin: true
      }
    ]
  }
});

